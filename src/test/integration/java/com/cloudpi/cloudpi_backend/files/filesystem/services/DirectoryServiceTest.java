package com.cloudpi.cloudpi_backend.files.filesystem.services;

import com.cloudpi.cloudpi_backend.files.filesystem.entities.VirtualDriveEntity;
import com.cloudpi.cloudpi_backend.files.utils.AddBasicDiscDrive;
import com.cloudpi.cloudpi_backend.files.utils.UserBasicPathCreator;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class DirectoryServiceTest {

    @Autowired
    DirectoryService directoryService;
    @Autowired
    AddBasicDiscDrive addBasicDiscDrive;
    @Autowired
    UserBasicPathCreator basicPathCreator;

    @BeforeEach
    void setUpDiscAndDrive() {
        addBasicDiscDrive.createDefaultDiscAndDrive();
    }

    @AfterEach
    void cleanUpDiscAndDrive() {
        addBasicDiscDrive.deleteAllDiscAndDrives();
    }

    void createUserWithVirtualDrive() {

    }

}

class CreateDirectory {

    void should_crete_new_directory() {

    }

}