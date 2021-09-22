package com.cloudpi.cloudpi_backend.utils;

import com.cloudpi.cloudpi_backend.security.dto.CloudPiAuthentication;
import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class AuthenticationUtils {

    public static Authentication setAuthentication(UserDTO user, Collection<? extends GrantedAuthority> authorities) {
        var auth = new CloudPiAuthentication(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        return auth;
    }


    public static Authentication setAuthentication(UserEntity user, Collection<? extends GrantedAuthority> authorities) {
        var auth = new CloudPiAuthentication(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        return auth;
    }

}