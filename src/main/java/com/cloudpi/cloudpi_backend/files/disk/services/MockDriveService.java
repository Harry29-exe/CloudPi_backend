package com.cloudpi.cloudpi_backend.files.disk.services;

import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

//TODO write serwice that is not a mock
@Service
public class MockDriveService implements DrivesService{

    private final Path root = Paths.get("/home/kamil/spring-test/");

    @Override
    public Path getPathToSaveFile(Long fileSize, String fileName) {
        return root.resolve(fileName);
    }
}
