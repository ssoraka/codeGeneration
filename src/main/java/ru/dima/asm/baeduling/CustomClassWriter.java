package ru.dima.asm.baeduling;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CustomClassWriter {

    static String className = "ru.dima.asm.baeduling.Test";
    static String cloneableInterface = "java/lang/Cloneable";
    ClassReader reader;
    ClassWriter writer;

    AddFieldAdapter addFieldAdapter;
    PublicizeMethodAdapter pubMethAdapter;

    public CustomClassWriter() throws IOException {
        reader = new ClassReader(className);
        writer = new ClassWriter(reader, 0);
    }

    public void addField() throws IOException {
        addFieldAdapter = new AddFieldAdapter(
                "aNewBooleanField",
                "java/lang/Boolean",
                org.objectweb.asm.Opcodes.ACC_PUBLIC,
                writer);
        reader.accept(addFieldAdapter, 0);
    }

    public void publicizeMethod() {
        pubMethAdapter = new PublicizeMethodAdapter(writer);
        reader.accept(pubMethAdapter, 0);
    }

    public byte[] getBytes() {
        return writer.toByteArray();
    }

    public static void main(String[] args) throws IOException {
        CustomClassWriter writer1 = new CustomClassWriter();
        writer1.addField();

        byte[] bytes = writer1.getBytes();

        FileOutputStream outputStream = new FileOutputStream("target/classes/ru/dima/asm/baeduling/Test.class");
        outputStream.write(bytes);
        outputStream.close();


        writer1 = new CustomClassWriter();
        writer1.publicizeMethod();

        bytes = writer1.getBytes();

        outputStream = new FileOutputStream("target/classes/ru/dima/asm/baeduling/Test.class");
        outputStream.write(bytes);
        outputStream.close();

    }

}
