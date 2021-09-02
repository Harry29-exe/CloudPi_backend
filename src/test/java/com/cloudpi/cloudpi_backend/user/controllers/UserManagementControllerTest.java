package com.cloudpi.cloudpi_backend.user.controllers;

import com.cloudpi.cloudpi_backend.security.AccountType;
import com.cloudpi.cloudpi_backend.user.dto.UserDto;
import com.cloudpi.cloudpi_backend.user.services.UserRepoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.*;

import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
class UserManagementControllerTest {

    private UserRepoService userService = Mockito.mock(UserRepoService.class);
    private ModelMapper modelMapper = new ModelMapper();
    private UserManagementController userController = new UserManagementController(userService, modelMapper, "true");


    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserRepoService.class);
        modelMapper = new ModelMapper();
        userController = new UserManagementController(userService, modelMapper, "true");
    }

    private void setModificationsOnlyFromLocalNetwork(boolean value) {
        ReflectionTestUtils.setField(userController, "managementFromLocalNetworkOnly", value);
    }


    @Test
    void createNewUser() {
        //given
        setModificationsOnlyFromLocalNetwork(false);
        var userDto = new UserDto(1L, "root", "root@root", "root", "password", false, AccountType.ROOT, new ArrayList());
        doReturn(Optional.of(userDto)).when(userService).getUser("");
        //when
//        userController.createNewUser()

    }
}