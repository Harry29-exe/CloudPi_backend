package com.cloudpi.cloudpi_backend.files.filesystem.services;

import org.hibernate.engine.jdbc.internal.BinaryStreamImpl;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.*;

@Service
public class FileServiceImpl implements FileService {
    private final Path root = Paths.get("/home/kamil/spring-test/");

    @Override
    public void saveFile(String path, InputStream file) throws IOException {
        Files.copy(file, root.resolve(path));
    }

    @Override
    public void createDirectory(String path) {

    }

    @Override
    public Resource readFile(String path) {
        return null;
    }

    @Override
    public void deleteFile(String path) {

    }
}
