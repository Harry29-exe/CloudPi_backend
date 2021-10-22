package com.cloudpi.cloudpi_backend.authorities.endpoint;

import com.cloudpi.cloudpi_backend.authorities.responses.GetAuthoritiesInfoResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthoritiesController implements AuthoritiesAPI {

    @Override
    public GetAuthoritiesInfoResponse getAvailableAuthorities(Authentication auth) {
        return null;
    }

    @Override
    public List<String> getAuthorities(String username) {
        return null;
    }

    @Override
    public void giveAuthorities() {

    }
}
