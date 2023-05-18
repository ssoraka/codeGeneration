package my.processor;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
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
