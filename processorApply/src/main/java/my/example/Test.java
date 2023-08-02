package my.example;

import my.processor.MyAnnotation;

@MyAnnotation
public class Test {

    private int value;

//    @MyAnnotation
    public static void main(String[] args) {
        System.out.println("hello world");
        System.out.println("hello world");
        System.out.println("hello world");

        System.out.println(new Test().newField);
        new Test().newMethod();
    }
}
