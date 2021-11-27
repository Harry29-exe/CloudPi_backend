package com.cloudpi.cloudpi_backend.files.filesystem.services;

import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi_backend.files.utils.AddBasicDiscDrive;
import com.cloudpi.cloudpi_backend.user.dto.AccountType;
import com.cloudpi.cloudpi_backend.user.dto.CreateUserVal;
import com.cloudpi.cloudpi_backend.user.services.UserService;
import com.cloudpi.cloudpi_backend.utils.mock_mvc_users.WithUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;

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

}

@DisplayName("createDirectory")
@Transactional
class CreateDirectory extends DirectoryServiceTest {

    @BeforeEach
    void setUp() {
        setRootAuth();
        userService.createUserWithDefaultAuthorities(
                new CreateUserVal("root", "123", "mighty root", AccountType.ROOT, null)
        );
        userService.createUserWithDefaultAuthorities(
                new CreateUserVal("bob", "123","super bob", AccountType.USER, null)
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
        Assertions.assertThrows(PersistenceException.class, () -> {
            //when
            directoryService.createDirectory(newDirPath);
        });
    }

//    @Test
//    @WithUser(username = "Alice")
//    void should_throw_AccessDeniedException_when_access_without_file_permission() {
//        //given
//        var newDirPath = new VirtualPath("bob/newDir");
//        em.flush();
//        //then
//        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
//            //when
//            directoryService.createDirectory(newDirPath);
//            em.flush();
//        });
//    }

}