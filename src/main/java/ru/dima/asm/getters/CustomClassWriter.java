package ru.dima.asm.getters;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import ru.dima.asm.baeduling.AddFieldAdapter;
import ru.dima.asm.baeduling.PublicizeMethodAdapter;

import java.io.FileOutputStream;
import java.io.IOException;

public class CustomClassWriter {

    static String className = "ru.dima.asm.getters.Test";
    ClassReader reader;
    ClassWriter writer;

    GetterClassVisitor getterClassVisitor;


    public CustomClassWriter() throws IOException {
        reader = new ClassReader(className);
        writer = new ClassWriter(reader, 0);
    }

    public byte[] createGetters() {
        getterClassVisitor = new GetterClassVisitor(writer);
        reader.accept(getterClassVisitor, 0);
        return writer.toByteArray();
    }

    public static void main(String[] args) throws IOException {
        CustomClassWriter writer1 = new CustomClassWriter();
        var bytes = writer1.createGetters();

        FileOutputStream outputStream = new FileOutputStream("target/classes/ru/dima/asm/getters/Test.class");
        outputStream.write(bytes);
        outputStream.close();
    }

}
