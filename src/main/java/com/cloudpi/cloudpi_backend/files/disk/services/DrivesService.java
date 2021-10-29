package com.cloudpi.cloudpi_backend.files.disk.services;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.VirtualPath;

import java.nio.file.Path;

public interface DrivesService {

    Path getPathToSaveFile(Long fileSize, String fileName);

}
