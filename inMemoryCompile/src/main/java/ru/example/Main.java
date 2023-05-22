package ru.example;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        String clazzName = "HelloWorld";
        String code = "public class HelloWorld { public static void main(String[] args) { System.out.println(\"Hello World!\"); ru.example.Main.printHelloWorld(); } }";

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        JavaSourceFromString source = new JavaSourceFromString(clazzName, code);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        SimpleJavaFileManager fileManager = new SimpleJavaFileManager(compiler.getStandardFileManager(null, null, null));
        DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();
        List<String> compilerOptions = List.of("-Xlint:unchecked");
        List<JavaSourceFromString> sources = List.of(source);
        JavaCompiler.CompilationTask task = compiler.getTask(new OutputStreamWriter(outputStream), fileManager, collector, compilerOptions, null, sources);
        boolean success = task.call();

        if (!success || collector.getDiagnostics().size() > 0) {
            error(collector);
        }

        byte[] compiledBytes = fileManager.getCompiledBytes().get(clazzName);
        Class<?> compiledClass = new ByteArrayClassLoader().loadClass(clazzName, compiledBytes);

        Object instance = compiledClass.newInstance();
        compiledClass.getMethod("main", String[].class).invoke(instance, (Object) null);
    }


    private static void error(DiagnosticCollector<JavaFileObject> collector) {
        StringBuffer exceptionMsg = new StringBuffer();
        exceptionMsg.append("Unable to compile the source");
        boolean hasWarnings = false;
        boolean hasErrors = false;
        for (Diagnostic<? extends JavaFileObject> d : collector.getDiagnostics()) {
            switch (d.getKind()) {
                case NOTE:
                case MANDATORY_WARNING:
                case WARNING:
                    hasWarnings = true;
                    break;
                case OTHER:
                case ERROR:
                default:
                    hasErrors = true;
                    break;
            }
            exceptionMsg.append("\n").append("[kind=").append(d.getKind());
            exceptionMsg.append(", ").append("line=").append(d.getLineNumber());
            exceptionMsg.append(", ").append("message=").append(d.getMessage(Locale.US)).append("]");
        }
        if (hasWarnings || hasErrors) {
            throw new RuntimeException(exceptionMsg.toString());
        }
    }


    public static void printHelloWorld() {
        System.out.println("Hello world 2!");
    }
}





