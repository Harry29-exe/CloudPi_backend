package com.cloudpi.cloudpi_backend.files.filesystem.services;

import com.cloudpi.cloudpi_backend.files.disk.services.DrivesService;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.VirtualPathDTO;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

@Service
public class FileServiceImpl implements FileService {
    private final DrivesService drivesService;

    public FileServiceImpl(DrivesService drivesService) {
        this.drivesService = drivesService;
    }

    @Override
    public void saveFile(VirtualPathDTO path, InputStream file) throws IOException {
        var physicalPath = drivesService.getPathToSaveFile((long) file.available(), 0L);
        Files.copy(file, physicalPath);
    }

    @Override
    public void createDirectory(VirtualPathDTO path) {

    }

    @Override
    public Resource readFile(VirtualPathDTO path) {
        return null;
    }

    @Override
    public void deleteFile(VirtualPathDTO path) {

    }
}
