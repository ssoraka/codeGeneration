package my.processor;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.Compiler;
import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourceSubjectFactory;
//import com.sun.tools.javac.tree.JCTree;
//import com.sun.tools.javac.util.Warner;
import org.junit.Test;

import javax.tools.ToolProvider;
import java.io.File;
import java.net.MalformedURLException;


public class MyProcessorTest {

    public void testProcess() {
    }

    @Test
    public void EmptyClassCompiles() throws MalformedURLException {
        final MyProcessor processor = new MyProcessor();
        File source = new File("my/processor/TestClass.java");

//        Warner warner = new Warner();

//        Compilation compile = Compiler.compiler(ToolProvider.getSystemJavaCompiler())
//                .withProcessors(processor)
//                .withOptions("--add-exports jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED",
//                        "--add-exports",
//                                "jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED")
//                .compile(JavaFileObjects.forSourceLines("my.processor.TestClass1",
//                        "package my.processor;\n" +
//                                "\n" +
//                                "@MyAnnotation\n" +
//                                "public class TestClass1 {\n" +
//                                "    private int value;\n" +
//                                "\n" +
//                                "    public static void main(String[] args) {\n" +
//                                "        System.out.println(\"hello world\");\n" +
//                                "    }\n" +
//                                "}"));
//        System.out.println(compile.status());


    }
}