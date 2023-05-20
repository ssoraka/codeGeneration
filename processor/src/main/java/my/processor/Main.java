package my.processor;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;

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
        ASTParser parser = ASTParser.newParser(AST.getJLSLatest());
        parser.setSource(a.toCharArray());
    }
}
