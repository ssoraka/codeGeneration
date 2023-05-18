package ru.dima.asm.getters;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.HashMap;
import java.util.Map;

import static org.objectweb.asm.Opcodes.*;

public class GetterClassVisitor extends ClassVisitor {
    private Map<String, Field> fieldsMap = new HashMap<>();

    public GetterClassVisitor(ClassVisitor cv) {
        super(ASM7, cv);
        this.cv = cv;
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        Field field = new Field(access, name, descriptor);
        fieldsMap.put(field.getterName(), field);
        return cv.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if (fieldsMap.containsKey(name) && descriptor.equals(fieldsMap.get(name).getterDescriptor())) {
            fieldsMap.remove(name);
        }
        return cv.visitMethod(access, name, descriptor, signature, exceptions);
    }

    @Override
    public void visitEnd() {

        for (Field field : fieldsMap.values()) {
            MethodVisitor vm = cv.visitMethod(ACC_PUBLIC, field.getterName(), field.getterDescriptor(), null, null);
            new MyMethodVisitor(Opcodes.ASM7, vm, field, "ru/dima/asm/getters/Test").visitCode();

            if (vm != null) {
                vm.visitEnd();
            }
        }

        cv.visitEnd();
    }
}

class MyMethodVisitor extends MethodVisitor {
    private Field field;
    private String className;

    public MyMethodVisitor(int api, MethodVisitor mv, Field field, String className) {
        super(api, mv);
        this.field = field;
        this.className = className;
    }

    @Override
    public void visitCode() {
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitFieldInsn(Opcodes.GETFIELD, className, field.getName(), field.getDescriptor());
        mv.visitInsn(returnOpcodesByDescriptor(field.getDescriptor()));
        mv.visitEnd();
    }

    private int returnOpcodesByDescriptor(String descriptor) {
        switch (descriptor) {
            case "C" :
            case "S" :
            case "Z" :
            case "I" : return Opcodes.IRETURN;
            case "D" : return Opcodes.DRETURN;
            case "J" : return Opcodes.LRETURN;
            case "F" : return Opcodes.FRETURN;
            default : return Opcodes.ARETURN;
        }
    }
}

class Field {
    int access;
    String name;
    String descriptor;

    public Field(int access, String name, String descriptor) {
        this.access = access;
        this.name = name;
        this.descriptor = descriptor;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getterName() {
        return "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1, name.length());
    }

    public String getterDescriptor() {
        return "()" + descriptor;
    }
}