package com.cloudpi.cloudpi_backend.files.filesystem.repositories;

import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi_backend.files.filesystem.services.DirectoryService;
import com.cloudpi.cloudpi_backend.user.services.UserService;
import com.cloudpi.cloudpi_backend.user.services.dto.CreateUser;
import com.cloudpi.cloudpi_backend.utils.mock_auth.AuthenticationSetter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
class PathRepoTest {

    @Autowired
    UserService userService;
    @Autowired
    DirectoryService dirService;
    @Autowired
    PathRepo pathRepo;

    @BeforeEach
    void setUp() {
        AuthenticationSetter.setRootAuth();
        userService.createUserWithDefaultAuthorities(
                new CreateUser("bob", "123", "mighty root", AccountType.ROOT, null)
        );
        dirService.create(new VirtualPath("bob/dir1"));
        dirService.create(new VirtualPath("bob/dir2"));
        dirService.create(new VirtualPath("bob/dir1/dir11"));
        AuthenticationSetter.clearAuth();
    }

    @Test
    @Transactional
    void selectParentsIds() {
        //given
        var dir = dirService.get(new VirtualPath("bob/dir1/dir11"));
        var ids = pathRepo.selectPathAndItsParentsIds(dir.getId());

        assert ids.size() == 3;
        ids.forEach(id -> {
            System.out.println(id.getEntityType() + ", " + id.getId() + ", " + id.getParentId());
        });
    }

    @Test
    @Transactional
    void selectChildrenIds() {
        //given
        var dir = dirService.get(new VirtualPath("bob/dir1"));
        var ids = pathRepo.selectPathAndItsChildrenIds(dir.getId());

        assert ids.size() == 2;
        ids.forEach(id -> {
            System.out.println(id.getEntityType() + ", " + id.getId() + ", " + id.getParentId());
        });
    }

}