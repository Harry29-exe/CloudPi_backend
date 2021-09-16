package com.cloudpi.cloudpi_backend.user.services.UserServiceImpTest;

import com.cloudpi.cloudpi_backend.authorization.dto.CPAuthority;
import com.cloudpi.cloudpi_backend.security.CloudPiAuthentication;
import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import com.cloudpi.cloudpi_backend.user.services.UserService;
import com.cloudpi.cloudpi_backend.user.services.UserServiceImp;
import com.google.common.collect.ImmutableCollection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public abstract class UserServiceImpTest {
    protected UserRepository userRepository;
    protected UserService userService;

    @BeforeAll
    public static void setUpAuthorityFactory() {

    }

    @BeforeEach
    public void initUserRepository() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImp(userRepository);
        this.removeAuthentication();
    }

    protected Authentication setAuthentication(
            UserDTO user,
            Collection<? extends CPAuthority> authorities) {
        var auth = new CloudPiAuthentication(user.getUsername(), user.getPassword(), authorities);
        auth.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(auth);

        return auth;
    }

    protected void removeAuthentication() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}