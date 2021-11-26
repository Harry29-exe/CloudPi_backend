package com.cloudpi.cloudpi_backend.files.filesystem.repositories;

import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi_backend.files.filesystem.services.DirectoryService;
import com.cloudpi.cloudpi_backend.utils.mock_auth.AuthenticationSetter;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import com.cloudpi.cloudpi_backend.user.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
class PathRepositoryTest {

    @Autowired
    UserService userService;
    @Autowired
    DirectoryService dirService;
    @Autowired
    PathRepository pathRepository;

    @BeforeEach
    void setUp() {
        AuthenticationSetter.setRootAuth();
        userService.createUserWithDefaultAuthorities(
                new UserWithDetailsDTO("super bob", "bob"),
                "123"
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
        var dir = dirService.getDirectoryDto(new VirtualPath("bob/dir1/dir11"));
        var ids = pathRepository.selectPathAndItsParentsIds(dir.getId());

        assert ids.size()  == 3;
        ids.forEach(id -> {
            System.out.println(id.getEntityType() + ", " + id.getId() +", " + id.getParentId());
        });
    }

    @Test
    @Transactional
    void selectChildrenIds() {
        //given
        var dir = dirService.getDirectoryDto(new VirtualPath("bob/dir1"));
        var ids = pathRepository.selectPathAndItsChildrenIds(dir.getId());

        assert ids.size()  == 2;
        ids.forEach(id -> {
            System.out.println(id.getEntityType() + ", " + id.getId() +", " + id.getParentId());
        });
    }

}