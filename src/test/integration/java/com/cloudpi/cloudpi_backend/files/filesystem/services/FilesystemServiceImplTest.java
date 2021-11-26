package com.cloudpi.cloudpi_backend.files.filesystem.services;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileStructureDTO.FSDirectoryDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi_backend.user.dto.AccountType;
import com.cloudpi.cloudpi_backend.user.dto.CreateUserVal;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import com.cloudpi.cloudpi_backend.user.services.UserService;
import com.cloudpi.cloudpi_backend.utils.mock_auth.AuthenticationSetter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@ActiveProfiles("test")
class FilesystemServiceImplTest {

    @Autowired
    UserService userService;
    @Autowired
    DirectoryService dirService;
    @Autowired
    FilesystemService fsInfoService;

    @BeforeEach
    void setUp() {
        AuthenticationSetter.setRootAuth();
        userService.createUserWithDefaultAuthorities(
                new CreateUserVal("bob", "123", "super bob", AccountType.USER, null)
        );
        dirService.createDirectory(new VirtualPath("bob/dir1"));
        dirService.createDirectory(new VirtualPath("bob/dir2"));
        dirService.createDirectory(new VirtualPath("bob/dir1/dir11"));
        AuthenticationSetter.clearAuth();
    }

    @Test
    @Transactional
    void selectParentsIds() {
        //given


        var fs = fsInfoService.getFileStructure(0, new VirtualPath("bob"));
        printFS(fs.getRootDirectory(), 0);

    }

    private void printFS(FSDirectoryDTO fsDir, int level) {
        String offset = " ".repeat(level * 4);
        System.out.println(offset + fsDir.getDetails().getName());
        fsDir.getDirectories().forEach(d -> printFS(d, level + 1));
        fsDir.getFiles().forEach(f ->
                System.out.println(offset + f.getDetails().getName())
        );

    }

}