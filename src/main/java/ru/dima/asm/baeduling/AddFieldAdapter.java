package ru.dima.asm.baeduling;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;

import static org.objectweb.asm.Opcodes.ASM7;

public class AddFieldAdapter extends ClassVisitor {
    private String fieldName;
    private String fieldType;
    private int access = org.objectweb.asm.Opcodes.ACC_PUBLIC;
    private boolean isFieldPresent;

    public AddFieldAdapter(String fieldName, String fieldType, int fieldAccess, ClassVisitor cv) {
        super(ASM7, cv);
        this.cv = cv;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.access = fieldAccess;
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        if (name.equals(fieldName)) {
            isFieldPresent = true;
        }
        return cv.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public void visitEnd() {
        if (!isFieldPresent) {
            FieldVisitor fv = cv.visitField(access, fieldName, fieldType, null, null);
            if (fv != null) {
                fv.visitEnd();
            }
        }
        cv.visitEnd();
    }
}
