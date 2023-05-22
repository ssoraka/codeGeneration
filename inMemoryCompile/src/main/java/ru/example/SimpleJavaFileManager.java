package ru.example;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SimpleJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {
    private final Map<String, ByteArrayOutputStream> compiledBytes = new HashMap<>();

    protected SimpleJavaFileManager(JavaFileManager fileManager) {
        super(fileManager);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        compiledBytes.put(className, outputStream);
        return new SimpleJavaFileObject(URI.create(className), kind) {
            @Override
            public OutputStream openOutputStream() {
                return outputStream;
            }
        };
    }

    public Map<String, byte[]> getCompiledBytes() {
        return compiledBytes.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toByteArray()));
    }
}