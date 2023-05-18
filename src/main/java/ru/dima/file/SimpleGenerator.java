package ru.dima.file;

import com.sun.codemodel.*;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleGenerator {

    static Path path = Paths.get(".")
            .resolve("src")
            .resolve("main")
            .resolve("java").toAbsolutePath();

    public static void main(String[] arg) throws JClassAlreadyExistsException, IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        generateTestClass();
        compile();
        load();
    }

    public static void generateTestClass() throws JClassAlreadyExistsException, IOException {
        //https://github.com/esfand/printit/blob/master/codemodel-tutorial.md
        //https://habr.com/ru/articles/688462/
        //https://github.com/trung/InMemoryJavaCompiler/blob/master/src/main/java/org/mdkt/compiler/DynamicClassLoader.java

        //создаем модель, это своего рода корень вашего дерева кода
        JCodeModel codeModel = new JCodeModel();

        //определяем наш класс Habr в пакете hello
        JDefinedClass annotate = codeModel._class("hello.Annotation", ClassType.ANNOTATION_TYPE_DECL);


        //определяем наш класс Habr в пакете hello
        JDefinedClass testClass = codeModel._class("hello.Habr")
                ._extends(SimpleGenerator.class)
                ._implements(Serializable.class);

        testClass.generify("T");
        testClass.annotate(annotate);


        JFieldVar field = (JFieldVar) testClass.field(JMod.PRIVATE + JMod.STATIC,
                        String.class,
                        "message")
                .init(JExpr.lit("Hello Habr!"));

        // определяем метод helloHabr
        JMethod method = testClass.method(JMod.PUBLIC + JMod.STATIC, codeModel.VOID, "helloHabr");

        // в теле метода выводим строку "Hello Habr!"
        method.body().directStatement("System.out.println(message);");


        JDocComment comment = testClass.javadoc();
        String commentString = "The <code>" + "hello.Habr" + "</code> " +
                "implementing the "  + Serializable.class.getSimpleName() + " interface";
        comment.append(commentString);



        //собираем модель и пишем пакеты в currentDirectory
        codeModel.build(path.toFile());
    }

    public static void compile()  throws IOException{
        Path srcPath = path.resolve("hello").toAbsolutePath();

        List<File> files = Files.list(srcPath)
                .map(Path::toFile)
                .filter(file -> file.getName().endsWith(".java"))
                .collect(Collectors.toList());
//получаем компилятор
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//получаем новый инстанс fileManager для нашего компилятора
        try(StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null)) {
            //получаем список всех файлов описывающих исходники
            Iterable<? extends JavaFileObject> javaFiles = fileManager.getJavaFileObjectsFromFiles(files);

            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
            //заводим задачу на компиляцию
            JavaCompiler.CompilationTask task = compiler.getTask(
                    null,
                    fileManager,
                    diagnostics,
                    null,
                    null,
                    javaFiles
            );
            //выполняем задачу
            task.call();
            //выводим ошибки, возникшие в процессе компиляции
            for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
                System.out.format("Error on line %d in %s%n",
                        diagnostic.getLineNumber(),
                        diagnostic.getSource());
            }
        }
    }


    private static void load() throws MalformedURLException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException {
//получаем ClassLoader
        ClassLoader classLoader = SimpleGenerator.class.getClassLoader();
//получаем путь до нашей папки со сгенерированным кодом
        URLClassLoader urlClassLoader = new URLClassLoader(
                new URL[]{Paths.get(".").toUri().toURL()},
                classLoader);
//загружаем наш класс
        Class<?> helloHabrClass = urlClassLoader.loadClass("hello.Habr");
//находим и вызываем метод helloHabr
        Method methodHelloHabr = helloHabrClass.getMethod("helloHabr");
//в параметре передается ссылка на экземпляр класса для вызова метода
//либо null при вызове статического метода
        methodHelloHabr.invoke(null);
    }


}
