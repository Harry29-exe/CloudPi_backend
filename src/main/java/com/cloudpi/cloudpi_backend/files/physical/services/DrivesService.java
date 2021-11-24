package com.cloudpi.cloudpi_backend.files.physical.services;

import java.nio.file.Path;
import java.util.UUID;

public interface DrivesService {

    Long getDriveIdAndReserveSpaceOnIt(Long fileSize);

    void freeDriveSpace(Long driveId, Long space);

    Path fileIdToPath(UUID fileId);

}
