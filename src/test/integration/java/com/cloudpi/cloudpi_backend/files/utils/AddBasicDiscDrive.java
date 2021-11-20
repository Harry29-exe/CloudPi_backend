package com.cloudpi.cloudpi_backend.files.utils;

import com.cloudpi.cloudpi_backend.files.physical.entities.DiscEntity;
import com.cloudpi.cloudpi_backend.files.physical.entities.DriveEntity;
import com.cloudpi.cloudpi_backend.files.physical.repositories.DiscRepository;
import com.cloudpi.cloudpi_backend.files.physical.repositories.DriveRepository;
import com.cloudpi.cloudpi_backend.user.utils.UserEntityBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.cloudpi.cloudpi_backend.test.utils.mock_auth.AuthenticationSetter.clearAuth;
import static com.cloudpi.cloudpi_backend.test.utils.mock_auth.AuthenticationSetter.setRootAuth;

@Component
public class AddBasicDiscDrive {

    @Value("${cloud-pi.test-storage-path}")
    private String path;
    @Autowired
    private DiscRepository discRepository;
    @Autowired
    private DriveRepository driveRepository;

    public void createDefaultDiscAndDrive() {
        setRootAuth();
        Path dirPath;
        if (path.startsWith("~")) {
            dirPath = Paths.get(
                    System.getProperty("user.home") + "/" +path.substring(1)
            );
        } else {
            dirPath = Paths.get(path);
        }

        try {
            var pathToDisc = Files.getFileStore(dirPath);
            var discEntity = new DiscEntity(pathToDisc.name());
            var driveEntity = new DriveEntity(dirPath.toString(), 1_000_000L, discEntity);
            discEntity.setDrives(new ArrayList<>(List.of(driveEntity)));
            discRepository.save(discEntity);
            driveRepository.save(driveEntity);
        } catch (IOException ioException) {
            throw new IllegalStateException();
        }

        clearAuth();
    }

    public void deleteAllDiscAndDrives() {
        setRootAuth();
        driveRepository.deleteAll();
        discRepository.deleteAll();
        clearAuth();
    }

}
