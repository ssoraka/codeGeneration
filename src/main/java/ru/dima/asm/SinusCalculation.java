package ru.dima.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SinusCalculation {

    static int dima1;

    int dima2;

    private double calculation(double x, double y) {
        return 2.0d * Math.sin((x + y) / 2.0d) * Math.cos((x - y) / 2.0d);
    }

    private static final String CLASS_LOCATION = "target/classes/ru/dima/asm/SinusCalculation.class";

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            throw new IllegalArgumentException("Expecting 2 parameters X and Y");
        }
        double result = new SinusCalculation().calculation(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
        System.out.println(result);

        FileInputStream is = new FileInputStream(CLASS_LOCATION);


        ClassReader cr = new ClassReader(is);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        cr.accept(new SinusVisitor(Opcodes.ASM7, cw), 0);

        FileOutputStream fos = new FileOutputStream(CLASS_LOCATION);
        fos.write(cw.toByteArray());
        fos.close();
    }
}