package my.processor;

import com.sun.source.util.Trees;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementScanner8;

public class BindViewVisitor  extends ElementScanner8<Void, Void> {

//    private final CodeBlock.Builder mFindViewById = CodeBlock.builder();

    private final Trees mTrees;

    private final Messager mLogger;

    private final Filer mFiler;

    private final Element mOriginElement;

//    private final TreeMaker mTreeMaker;

//    private final Names mNames;

    public BindViewVisitor(ProcessingEnvironment env, Element element) {
        super();
        mTrees = Trees.instance(env);
        mLogger = env.getMessager();
        mFiler = env.getFiler();
        mOriginElement = element;
//        final JavacProcessingEnvironment javacEnv = (JavacProcessingEnvironment) env;
//        mTreeMaker = TreeMaker.instance(javacEnv.getContext());
//        mNames = Names.instance(javacEnv.getContext());
    }

}
