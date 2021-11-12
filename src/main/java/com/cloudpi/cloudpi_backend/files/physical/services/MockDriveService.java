package com.cloudpi.cloudpi_backend.files.physical.services;

import com.cloudpi.cloudpi_backend.files.physical.entities.DriveEntity;
import com.cloudpi.cloudpi_backend.files.physical.repositories.DriveRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

//TODO write serwice that is not a mock
@Service
public class MockDriveService implements DrivesService {
    private final DriveRepository driveRepository;

    public MockDriveService(DriveRepository driveRepository) {
        this.driveRepository = driveRepository;
    }

    private final Path root = Paths.get("/home/kamil/spring-test/");
    private final Base64.Encoder encoder = Base64.getEncoder();
    private final Base64.Decoder decoder = Base64.getDecoder();

    @Override
    public Long getDriveIdAndReserveSpaceOnIt(Long fileSize) {
        return driveRepository.findAll().get(0).getId();
    }

    @Override
    @Transactional
    public void freeDriveSpace(Long driveId, Long space) {
        DriveEntity drive = driveRepository.findAll().get(0);
        drive.setSpaceInUse(drive.getSpaceInUse() + space);
    }

    @Override
    public Path fileIdToPath(UUID fileId, Long driveId) {
        return root.resolve(fileId.toString());
    }
}
