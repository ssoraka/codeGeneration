package ru.dima.asm.baeduling;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class PublicizeMethodAdapter extends ClassVisitor {
    public PublicizeMethodAdapter(ClassVisitor cv) {
        super(ASM7, cv);
        this.cv = cv;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if (name.equals("toUnsignedString0")) {
            return cv.visitMethod(
                    ACC_PUBLIC + ACC_STATIC,
                    name,
                    descriptor,
                    signature,
                    exceptions);
        }
        return this.cv.visitMethod(access, name, descriptor, signature, exceptions);
    }

    @Override
    public void visitEnd() {
        MethodVisitor vm = cv.visitMethod(ACC_PUBLIC + ACC_STATIC, "<init>", "()V", null, null);
        if (vm != null) {
            vm.visitEnd();
        }
        super.visitEnd();
    }
}