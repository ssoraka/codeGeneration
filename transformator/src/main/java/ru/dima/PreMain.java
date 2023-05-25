package ru.dima;

import java.lang.instrument.Instrumentation;

public class PreMain {
    public static void premain(String agentArgs, Instrumentation inst) {
        // Your code here
        System.out.println("Java Agent is running!");

        // Register a ClassFileTransformer
        inst.addTransformer(new MyClassTransformer());
    }
}
