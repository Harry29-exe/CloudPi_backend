package com.cloudpi.cloudpi_backend.user.services.UserServiceImpTest;

import com.cloudpi.cloudpi_backend.security.CloudPiAuthentication;
import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import com.cloudpi.cloudpi_backend.user.services.UserService;
import com.cloudpi.cloudpi_backend.user.services.UserServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class UserServiceImpTest {
    protected UserRepository userRepository;
    protected UserService userService;

    @BeforeEach
    public void initUserRepository() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImp(userRepository);
        this.removeAuthentication();
    }

    protected Authentication setAuthentication(UserDTO user) {
        var auth = new CloudPiAuthentication(user.toCloudPiUser());
        auth.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(auth);

        return auth;
    }

    protected void removeAuthentication() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}