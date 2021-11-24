package com.cloudpi.cloudpi_backend.files.filesystem.services;

import com.cloudpi.cloudpi_backend.exepctions.files.PathAlreadyExistException;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi_backend.files.utils.AddBasicDiscDrive;
import com.cloudpi.cloudpi_backend.utils.mock_mvc_users.WithUser;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import com.cloudpi.cloudpi_backend.user.services.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static com.cloudpi.cloudpi_backend.utils.mock_auth.AuthenticationSetter.clearAuth;
import static com.cloudpi.cloudpi_backend.utils.mock_auth.AuthenticationSetter.setRootAuth;

@SpringBootTest
@AutoConfigureMockMvc
@Import(AddBasicDiscDrive.class)
@ActiveProfiles("test")
class DirectoryServiceTest {

    @Autowired
    DirectoryService directoryService;
    @Autowired
    AddBasicDiscDrive addBasicDiscDrive;
    @Autowired
    UserService userService;

    @BeforeEach
    void setUpDiscAndDrive() {
        addBasicDiscDrive.createDefaultDiscAndDrive();
    }

    @AfterEach
    void cleanUpDiscAndDrive() {
        addBasicDiscDrive.deleteAllDiscAndDrives();
    }

}

@DisplayName("createDirectory")
@Transactional
class CreateDirectory extends DirectoryServiceTest {

    @BeforeEach
    void setUp() {
        setRootAuth();
        userService.createUserWithDefaultAuthorities(
                new UserWithDetailsDTO("mighty_root", "root"),
                "123"
        );
        userService.createUserWithDefaultAuthorities(
                new UserWithDetailsDTO("super_Bob", "bob"),
                "321"
        );
        clearAuth();
    }

    @Test
    @WithUser(username = "bob")
    void should_crete_new_directory() {
        //given
        var newDirPath = new VirtualPath("bob/newDir");

        //when
        directoryService.createDirectory(newDirPath);

        //then
        assert directoryService.getDirectoryDto(newDirPath) != null;
    }

    @Test
    @WithUser(username = "bob")
    void should_throw_PathAlreadyExistException_when_path_is_already_taken() {
        //given
        var newDirPath = new VirtualPath("bob/newDir");
        directoryService.createDirectory(newDirPath);

        //then
        Assertions.assertThrows(PathAlreadyExistException.class, () -> {
            //when
            directoryService.createDirectory(newDirPath);
        });
    }

    @Test
    @WithUser(username = "Alice")
    void should_throw_AccessDeniedException_when_access_without_file_permission() {
        //given
        var newDirPath= new VirtualPath("bob/newDir");

        //then
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            //when
            directoryService.createDirectory(newDirPath);
        });
    }

}