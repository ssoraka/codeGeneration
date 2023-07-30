package my.processor;

import com.sun.source.tree.*;
import com.sun.source.util.SimpleTreeVisitor;
import com.sun.source.util.Trees;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Name;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
//import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;

@SupportedAnnotationTypes("my.processor.MyAnnotation")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MyProcessor extends AbstractProcessor {

    private JavacElements elementUtils;
    private JavacProcessingEnvironment javacProcessingEnv;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = (JavacElements)processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(MyAnnotation.class)) {
            // Generate code programmatically


            if (processingEnv instanceof JavacProcessingEnvironment) {
                javacProcessingEnv = (JavacProcessingEnvironment) processingEnv;
            } else {
                throw new RuntimeException("JavacProcessingEnvironment not available.");
            }

            TreeMaker treeMaker = TreeMaker.instance(javacProcessingEnv.getContext());
            JavacTrees trees = (JavacTrees)Trees.instance(processingEnv);

            Name fieldName = elementUtils.getName("newField");
            Name fieldType = elementUtils.getName("Integer");
            JCTree.JCVariableDecl newField = treeMaker.VarDef(treeMaker.Modifiers(Flags.AccPrivate), fieldName, treeMaker.Ident(fieldType), null);

            JCTree.JCMethodDecl newMethod = treeMaker.MethodDef(
                    treeMaker.Modifiers(Flags.AccPublic),
                    elementUtils.getName("newMethod"),
                    treeMaker.TypeIdent(TypeTag.VOID),
                    com.sun.tools.javac.util.List.nil(), // типы параметров метода
                    com.sun.tools.javac.util.List.nil(), // параметры метода
                    com.sun.tools.javac.util.List.nil(), // список типов исключений
                    treeMaker.Block(0, com.sun.tools.javac.util.List.nil()), // тело метода
                    null // значение по умолчанию
            );


/*
            if (element.getKind() == ElementKind.CLASS) {
                TypeElement typeElement = (TypeElement) element;
                // Get the source code and parse it with Eclipse JDT ASTParser
                String sourceCode = getSourceCode(element);

                Document document = new Document(sourceCode);

                ASTParser parser = ASTParser.newParser(AST.getJLSLatest());
                parser.setResolveBindings(true);
                parser.setKind(ASTParser.K_COMPILATION_UNIT);
                parser.setBindingsRecovery(true);
                parser.setSource(sourceCode.toCharArray());
                CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);


                compilationUnit.accept(new ASTVisitor() {


                    @Override
                    public boolean visit(CompilationUnit node) {
//                        Expression newArgument = node.getAST().newSimpleName("newArgument");
                        return super.visit(node);
                    }

                    @Override
                    public boolean visit(FieldDeclaration node) {
                        if (Flags.isPrivate(node.getFlags())) {
                            ChildListPropertyDescriptor modifiersProperty = node.getModifiersProperty();

//                            int flag = (node.getFlags() & ~Flags.AccPrivate) | Flags.AccPublic;
                            node.setFlags(Flags.AccPublic);
                        }
                        return super.visit(node);
                    }

                    @Override
                    public boolean visit(VariableDeclarationExpression node) {
                        return super.visit(node);
                    }

                    @Override
                    public boolean visit(VariableDeclarationFragment node) {
                        node.getName().setIdentifier("var");
                        return super.visit(node);
                    }

                    @Override
                    public boolean visit(MethodDeclaration node) {
                        AST ast = node.getAST();
                        MethodInvocation methodInvocation = ast.newMethodInvocation();

                        QualifiedName qName = ast.newQualifiedName(ast.newSimpleName("System"), ast.newSimpleName("out"));
                        methodInvocation.setExpression(qName);
                        methodInvocation.setName(ast.newSimpleName("println"));

                        StringLiteral literal = ast.newStringLiteral();
                        literal.setLiteralValue("Hello, World");
                        methodInvocation.arguments().add(literal);

                        // Append the statement
                        node.getBody().statements().add(ast.newExpressionStatement(methodInvocation));

                        return super.visit(node);
                    }
                });






                try {


                    ASTRewrite rewrite = ASTRewrite.create(compilationUnit.getAST());
//                    rewrite.
                    TextEdit edits = rewrite.rewriteAST(document, null);
//https://www.immagic.com/eLibrary/ARCHIVES/GENERAL/ECLPS_CA/E050627I.pdf
                    edits.apply(document);

                    System.out.println(document.get());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }


//                compilationUnit.accept(new ASTVisitor() {
//                    @Override
//                    public boolean visit(MethodInvocation node) {
//                        // Check if the method invocation matches your criteria for modification
//                        if (node.getName().getIdentifier().equals("main")) {
//                            // Modify the arguments of the method invocation
//                            Expression newArgument = compilationUnit.getAST().newSimpleName("newArgument");
//                            node.arguments().add(newArgument);
//                        }
//                        return super.visit(node);
//                    }
//                });


            }
*/


            TreeVisitor<Void, Void> objectObjectTreeVisitor = new SimpleTreeVisitor<>() {

                @Override
                public Void visitClass(ClassTree node, Void unused) {

                    for (Tree tree : node.getMembers()) {
                        if (tree.getKind().equals(Tree.Kind.VARIABLE)) {
                            JCVariableDecl tree1 = (JCVariableDecl) tree;
                            tree1.mods.flags = 1L;
                        }
                    }

                    JCTree.JCClassDecl node1 = (JCTree.JCClassDecl) node;
                    node1.defs = node1.defs.append(newMethod);
                    node1.defs = node1.defs.append(newField);
                    node1.defs = com.sun.tools.javac.util.List.from(node1.defs);


                    return super.visitClass(node, unused);
                }
            };
            Tree tree = trees.getTree(element);
            tree.accept(objectObjectTreeVisitor, null);


//            final JavacProcessingEnvironment javacEnv = (JavacProcessingEnvironment) processingEnv1;
//            mTreeMaker = TreeMaker.instance(javacEnv.getContext());


            String className = element.getSimpleName().toString();
            String generatedCode = generateCode(className);
            writeGeneratedCodeToFile(className, generatedCode);
        }
        return true;
    }


    // Helper method to get the source code of a TypeElement
    private String getSourceCode(Element typeElement) {
        // Use the obtained source code as needed
        try {
//            return null;
            return Files.readString(Path.of(elementUtils.getFileObjectOf(typeElement).toUri()), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object getFromField(Object from, String name) {
        try {
            Class<?> aClass = from.getClass();
            Field field = aClass.getDeclaredField(name);
            field.setAccessible(true);
            Object to = field.get(from);
            field.setAccessible(false);
            return to;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private String generateCode(String className) {
        // Generate code based on the annotated element
        // ...
        return className;
    }

    private void writeGeneratedCodeToFile(String className, String code) {
        // Write the generated code to a file
        // ...
        System.out.println(className);
    }
}
