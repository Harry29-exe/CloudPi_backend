package com.cloudpi.cloudpi_backend.files.disk.services;

import java.nio.file.Path;

public interface DrivesService {

    Path getPathToSaveFile(Long fileSize, Long fileId);

    Path fileIdToPath(Long fileId);

}
