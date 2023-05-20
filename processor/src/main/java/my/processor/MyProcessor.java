package my.processor;

import com.sun.source.tree.*;
import com.sun.source.util.SimpleTreeVisitor;
import com.sun.source.util.Trees;
import org.eclipse.jdt.core.dom.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
//import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

@SupportedAnnotationTypes("my.processor.MyAnnotation")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MyProcessor extends AbstractProcessor {

    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(MyAnnotation.class)) {
            // Generate code programmatically
            ProcessingEnvironment processingEnv1 = processingEnv;
            Trees trees = Trees.instance(processingEnv1);
            Object object = element.getEnclosingElement();



            if (element.getKind() == ElementKind.CLASS) {
                TypeElement typeElement = (TypeElement) element;
                // Get the source code and parse it with Eclipse JDT ASTParser
                String sourceCode = getSourceCode(element);
                ASTParser parser = ASTParser.newParser(AST.getJLSLatest());
                parser.setSource(sourceCode.toCharArray());
                CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

                // Modify the AST as needed
                // For example, add a new method declaration
                AST ast = AST.newAST(AST.getJLSLatest(), true);

                // Create a new method declaration
                MethodDeclaration newMethod = ast.newMethodDeclaration();
                newMethod.setName(ast.newSimpleName("myMethod"));
                newMethod.setReturnType2(ast.newPrimitiveType(PrimitiveType.VOID));
                // Set any other properties of the new method

                // Add the method to the class body
                TypeDeclaration typeDeclaration2 = ast.newTypeDeclaration();
                typeDeclaration2.bodyDeclarations().add(newMethod);

                // Create a compilation unit and set the modified type declaration
                CompilationUnit compilationUnit2 = ast.newCompilationUnit();
                compilationUnit.types().add(typeDeclaration2);

                // Set properties of the new method
                // ...
                // Add the new method to the class body
                TypeDeclaration typeDeclaration = (TypeDeclaration) compilationUnit.types().get(0);
                typeDeclaration.bodyDeclarations().add(newMethod);

                // Generate the modified code
                String modifiedCode = compilationUnit.toString();

                // Output the modified code for verification
//                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, modifiedCode);
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, compilationUnit2.toString());
            }



            TreeVisitor<Void, Void> objectObjectTreeVisitor = new SimpleTreeVisitor<>() {

                @Override
                public Void visitClass(ClassTree node, Void unused) {

                    node.getMembers().stream()
                            .filter(tree -> tree.getKind().equals(Tree.Kind.VARIABLE))
                            .map(tree -> {
                                VariableTree modifiers = ((VariableTree) tree);
                                return modifiers;
                            }).count();
                    return super.visitClass(node, unused);
                }
            };
            Tree tree = trees.getTree(element);
            tree.accept(objectObjectTreeVisitor, null);

            BindViewVisitor bindViewVisitor = new BindViewVisitor(processingEnv, element);

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
            return Files.readString(Path.of(elementUtils.getFileObjectOf(typeElement).toUri()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
