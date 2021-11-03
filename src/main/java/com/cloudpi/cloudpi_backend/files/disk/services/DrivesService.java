package com.cloudpi.cloudpi_backend.files.disk.services;

import org.springframework.data.util.Pair;

import java.nio.file.Path;

public interface DrivesService {

    Pair<Long, Path> getDriveIdAndPathToSaveFile(Long fileSize, Long fileId);

    Path fileIdToPath(Long fileId);

}
