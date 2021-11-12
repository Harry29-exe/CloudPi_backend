package com.cloudpi.cloudpi_backend.files.physical.services;

import com.cloudpi.cloudpi_backend.files.physical.dto.DriveDTO;
import com.cloudpi.cloudpi_backend.files.physical.entities.DriveEntity;
import com.cloudpi.cloudpi_backend.utils.RepoService;

public interface DriveRepoService extends RepoService<DriveEntity, Long> {

    DriveDTO getDrive(Long driveId);

    DriveDTO getDriveByFileId(Long driveId);


}
