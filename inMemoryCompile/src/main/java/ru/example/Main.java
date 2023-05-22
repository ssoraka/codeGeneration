package ru.example;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        String clazzName = "HelloWorld";
        String code = "public class HelloWorld { public static void main(String[] args) { System.out.println(\"Hello, World!\"); } }";

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        JavaSourceFromString source = new JavaSourceFromString(clazzName, code);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        SimpleJavaFileManager fileManager = new SimpleJavaFileManager(compiler.getStandardFileManager(null, null, null));
        JavaCompiler.CompilationTask task = compiler.getTask(new OutputStreamWriter(outputStream), fileManager, null, null, null, List.of(source));
        boolean success = task.call();

        if (!success) {
            throw new Exception("Compilation failed");
        }

        byte[] compiledBytes = fileManager.getCompiledBytes().get(clazzName);
        Class<?> compiledClass = new ByteArrayClassLoader().loadClass(clazzName, compiledBytes);

        Object instance = compiledClass.newInstance();
        compiledClass.getMethod("main", String[].class).invoke(instance, (Object) null);
    }
}





