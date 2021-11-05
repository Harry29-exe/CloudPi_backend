package com.cloudpi.cloudpi_backend.files.disk.services;

import org.springframework.data.util.Pair;

import java.nio.file.Path;

public interface DrivesService {

    Long getDriveIdAndReserveSpaceOnIt(Long fileSize);

    Path fileIdToPath(Long fileId, Long driveId);

}
