package my.example;

import my.processor.MyAnnotation;

@MyAnnotation
public class Test {

    private int value;

    @MyAnnotation
    public static void main(String[] args) {
        System.out.println("hello world");
    }
}
