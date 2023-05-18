package ru.dima.memory;

import ru.dima.file.SimpleGenerator;

import javax.tools.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {

        String clazz = "Test1";

        String code = "fun main() {\n" +
                "    var str = \"Hello\"?.toLowerCase()\n" +
                "    println(str)\n" +
                "}\n";

        String finalCode = "public class Test1 {" +
                code.replace("println", "System.out.println")
                        .replace("fun", "public static void")
                        .replace("?.", "== null ? null : \"Hello\".")
                        .replaceAll("\n", ";\n")
                + "}\n";


        DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();
        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        SimpleJavaFileManager fileManager = new SimpleJavaFileManager(javac.getStandardFileManager(null, null, null));
        URI uri = URI.create("string:///" + clazz.replace('.', '/') + JavaFileObject.Kind.SOURCE.extension);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        SimpleJavaFileObject file = new SimpleJavaFileObject(uri, JavaFileObject.Kind.SOURCE) {
            public CharSequence getCharContent(boolean ignoreEncodingErrors) {
                return finalCode;
            }
            public OutputStream openOutputStream() throws IOException {
                return outputStream;
            }
        };
        JavaCompiler.CompilationTask task = javac.getTask(null, fileManager, collector, null, null, List.of(file));
        boolean result = task.call();

        //        Map<String, Class<?>> nameClassMap = InMemoryJavaCompiler.newInstance()
//                .useParentClassLoader(Main.class.getClassLoader())
//                .ignoreWarnings()
//                .addSource(clazz, code)
//                .compileAll();
//
//        Class<?> aClass = nameClassMap.get(clazz);

        byte[] compiledBytes = fileManager.getCompiledBytes().get("Test1");
        Class<?> compiledClass = new ByteArrayClassLoader().loadClass("Test1", compiledBytes);

        Method method = compiledClass.getMethod("main");
        method.invoke(null);
    }

    public static void main2(String[] args) throws Exception {
        String code = "public class HelloWorld { public static void main(String[] args) { System.out.println(\"Hello, World!\"); } }";

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        JavaSourceFromString source = new JavaSourceFromString("HelloWorld", code);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        SimpleJavaFileManager fileManager = new SimpleJavaFileManager(compiler.getStandardFileManager(null, null, null));
        JavaCompiler.CompilationTask task = compiler.getTask(new OutputStreamWriter(outputStream), fileManager, null, null, null, List.of(source));
        boolean success = task.call();

        if (!success) {
            throw new Exception("Compilation failed");
        }

        byte[] compiledBytes = fileManager.getCompiledBytes().get("HelloWorld");
        Class<?> compiledClass = new ByteArrayClassLoader().loadClass("HelloWorld", compiledBytes);

        Object instance = compiledClass.newInstance();
        compiledClass.getMethod("main", String[].class).invoke(instance, (Object) null);
    }
}
class JavaSourceFromString extends SimpleJavaFileObject {
    private final String code;

    protected JavaSourceFromString(String name, String code) {
        super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return code;
    }
}

class SimpleJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {
    private final Map<String, ByteArrayOutputStream> compiledBytes = new HashMap<>();

    protected SimpleJavaFileManager(JavaFileManager fileManager) {
        super(fileManager);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        compiledBytes.put(className, outputStream);
        return new SimpleJavaFileObject(URI.create(className), kind) {
            @Override
            public OutputStream openOutputStream() {
                return outputStream;
            }
        };
    }

    public Map<String, byte[]> getCompiledBytes() {
        return compiledBytes.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toByteArray()));
    }
}

class ByteArrayClassLoader extends ClassLoader {
    public Class<?> loadClass(String name, byte[] bytes) {
        return defineClass(name, bytes, 0, bytes.length);
    }
}


