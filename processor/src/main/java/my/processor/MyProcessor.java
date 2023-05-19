package my.processor;

import com.sun.source.tree.*;
import com.sun.source.util.SimpleTreeVisitor;
import com.sun.source.util.Trees;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
//import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import java.util.Set;

@SupportedAnnotationTypes("my.processor.MyAnnotation")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MyProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(MyAnnotation.class)) {
            // Generate code programmatically
            ProcessingEnvironment processingEnv1 = processingEnv;
            Trees trees = Trees.instance(processingEnv1);
            Object object = element.getEnclosingElement();




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
