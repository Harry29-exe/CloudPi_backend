package com.cloudpi.cloudpi_backend.authorities.api;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthoritiesController implements AuthoritiesAPI {

//    @Override
//    public GetAuthoritiesInfoResponse getAvailableAuthorities(Authentication auth) {
//        return null;
//    }

    @Override
    public List<String> getAuthorities(String username) {
        return null;
    }

    @Override
    public void giveAuthorities() {

    }
}
