package com.cloudpi.cloudpi_backend.user.services.UserServiceImpTest;

import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import com.cloudpi.cloudpi_backend.user.services.UserService;
import com.cloudpi.cloudpi_backend.user.services.UserServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public abstract class UserServiceImpTest {
    protected UserRepository userRepository;
    protected UserService userService;

    @BeforeEach
    public void initUserRepository() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImp(userRepository);
    }
}