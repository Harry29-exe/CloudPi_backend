package com.cloudpi.cloudpi_backend.files.filesystem.services;

import org.hibernate.engine.jdbc.internal.BinaryStreamImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.*;

@Service
public class FileServiceImpl {
    private final Path root = Paths.get("/home/kamil/spring-test/");

    public void saveFile(String path, byte[] file) throws IOException {
        var stream = new ByteArrayInputStream(file);
        Files.copy(stream, root.resolve(path));
    }
}
