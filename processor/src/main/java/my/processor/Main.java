package my.processor;

import com.sun.source.util.Trees;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.util.List;

public class Main {

    static String a = "package my.processor;\n" +
            "\n" +
            "import org.eclipse.jdt.core.dom.AST;\n" +
            "import org.eclipse.jdt.core.dom.ASTParser;\n" +
            "\n" +
            "public class Main {\n" +
            "    public static void main(String[] args) {\n" +
            "    }\n" +
            "}";

    public static void main(String[] args) {
//        ASTParser parser = ASTParser.newParser(AST.getJLSLatest());
//        parser.setSource(a.toCharArray());


        String clazzName = "HelloWorld";
        String code = "public class HelloWorld { public static void main(String[] args) { System.out.println(\"Hello World!\"); ru.example.Main.printHelloWorld(); } }";

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        URI uri = URI.create("string:///" + "HelloWorld" + JavaFileObject.Kind.SOURCE.extension);
        SimpleJavaFileObject source = new SimpleJavaFileObject(uri, JavaFileObject.Kind.SOURCE) {
            public CharSequence getCharContent(boolean ignoreEncodingErrors) {
                return code;
            }
        };

//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
//        DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();
//        List<String> compilerOptions = List.of("-Xlint:unchecked");
//        List<SimpleJavaFileObject> sources = List.of(source);
//        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, collector, compilerOptions, null, sources);
//
//        Trees instance = Trees.instance(task);

    }
}
