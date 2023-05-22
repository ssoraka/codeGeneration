package ru.example;

public class ByteArrayClassLoader extends ClassLoader {
    public Class<?> loadClass(String name, byte[] bytes) {
        return defineClass(name, bytes, 0, bytes.length);
    }
}
