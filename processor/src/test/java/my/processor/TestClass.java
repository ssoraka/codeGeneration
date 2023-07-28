package my.processor;

//import com.sun.source.util.Trees;

import javax.tools.*;
import java.net.URI;
import java.util.List;

public class TestClass {
    public static void main(String[] args) {
        String clazzName = "HelloWorld";
        String code = "public class HelloWorld { public static void main(String[] args) { System.out.println(\"Hello World!\"); } }";

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        URI uri = URI.create("string:///" + "HelloWorld" + JavaFileObject.Kind.SOURCE.extension);
        SimpleJavaFileObject source = new SimpleJavaFileObject(uri, JavaFileObject.Kind.SOURCE) {
            public CharSequence getCharContent(boolean ignoreEncodingErrors) {
                return code;
            }
        };

        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();
        List<String> compilerOptions = List.of("-Xlint:unchecked");
        List<SimpleJavaFileObject> sources = List.of(source);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, collector, compilerOptions, null, sources);

//        Trees instance = Trees.instance(task);
    }
}
