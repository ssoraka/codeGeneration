package ru.dima;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class MyClassTransformer implements ClassFileTransformer {

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            // Transform only specific classes
            if (className.equals("example/Test")) {
                // Use a bytecode manipulation library like Javassist
                ClassPool classPool = ClassPool.getDefault();
                CtClass ctClass = classPool.get(className.replace("/", "."));

                // Modify the class
                CtMethod getterMethod = CtMethod.make("public static int getNumber() { return 42; }", ctClass);
                ctClass.addMethod(getterMethod);

                // Return the modified bytecode
                return ctClass.toBytecode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return null to indicate no modifications were made
        return null;
    }
}
