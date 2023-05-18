package ru.dima.asm;

import org.objectweb.asm.*;

import java.io.*;

public class MyClassBytecodeModifier {

//    public static void main(String[] args) throws IOException {
//        // Read the existing class bytecode
//        InputStream inputStream = new FileInputStream("MyClass.class");
//        ClassReader classReader = new ClassReader(inputStream);
//
//
//        // Define the new field
//        FieldVisitor fieldVisitor = new FieldWriter(Opcodes.ACC_PRIVATE, "newField", "Ljava/lang/String;", null, null);
//
//        // Define a ClassVisitor that will add the new field to the existing class
//        ClassVisitor classVisitor = new ClassWriter(classReader, 0) {
//            @Override
//            public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
//                if (name.equals("newField")) {
//                    // The field already exists, don't add it again
//                    return null;
//                } else {
//                    // Visit the existing field
//                    return super.visitField(access, name, descriptor, signature, value);
//                }
//            }
//
//            @Override
//            public void visitEnd() {
//                // Add the new field to the class
//                fieldVisitor.visitEnd();
//                super.visitEnd();
//            }
//        };
//
//        // Visit the existing class with the ClassVisitor
//        classReader.accept(classVisitor, 0);
//
//        // Get the modified bytecode from the ClassWriter
//        byte[] modifiedClassBytecode = ((ClassWriter) classVisitor).toByteArray();
//
//        // Write the modified bytecode to a new file
//        OutputStream outputStream = new FileOutputStream("MyClassModified.class");
//        outputStream.write(modifiedClassBytecode);
//        outputStream.close();
//    }
}