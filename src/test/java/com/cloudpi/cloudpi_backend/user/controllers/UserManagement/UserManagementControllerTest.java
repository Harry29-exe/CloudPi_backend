package com.cloudpi.cloudpi_backend.user.controllers.UserManagement;

import com.cloudpi.cloudpi_backend.configuration.network.LocalNetwork;
import com.cloudpi.cloudpi_backend.configuration.network.LocalNetworksInfo;
import com.cloudpi.cloudpi_backend.user.controllers.UserManagementController;
import com.cloudpi.cloudpi_backend.user.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.*;

import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
public class UserManagementControllerTest {

    protected UserService userService;
    protected UserManagementController userController;
    protected ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        modelMapper = new ModelMapper();
        LocalNetworksInfo networksInfo = new LocalNetworksInfo(List.of(new LocalNetwork("192.168.0.0", 24)));
        userController = new UserManagementController(userService, networksInfo, null);
    }

    protected void setModificationsOnlyFromLocalNetwork(boolean value) {
        ReflectionTestUtils.setField(userController, "managementFromLocalNetworkOnly", value);
    }





}