package com.cloudpi.cloudpi_backend.utils;

import com.cloudpi.cloudpi_backend.files.physical.entities.DiscEntity;
import com.cloudpi.cloudpi_backend.files.physical.entities.DriveEntity;
import com.cloudpi.cloudpi_backend.files.physical.repositories.DiscRepository;
import com.cloudpi.cloudpi_backend.files.physical.repositories.DriveRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"test"})
public class BeforeAppStarts {

    private DiscRepository discRepo;
    private DriveRepository driveRepo;
    private String path;

    public BeforeAppStarts(
            DiscRepository discRepo,
            DriveRepository driveRepo, @Value("${cloud-pi.test-storage-path}") String path) {
        this.discRepo = discRepo;
        this.driveRepo = driveRepo;
        if(path.startsWith("~")) {
            this.path = System.getenv("user.home") + path;
        } else {
            this.path = path;
        }

        createDiscAndDrive();
    }

    private void createDiscAndDrive() {
        Long space = (long) Math.pow(10, 3);


        var disc = discRepo.save(
                new DiscEntity(path)
        );
        driveRepo.save(
                new DriveEntity(path + "cloud_test", space, disc)
        );
    }

}
