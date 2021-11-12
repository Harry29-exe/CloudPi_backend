package com.cloudpi.cloudpi_backend.authentication;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface CPUserDetailsService extends UserDetailsService {

    String findUsernameByLogin(String login);

}
