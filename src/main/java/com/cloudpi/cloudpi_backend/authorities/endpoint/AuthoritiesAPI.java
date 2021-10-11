package com.cloudpi.cloudpi_backend.authorities.endpoint;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/authorities")
public interface AuthoritiesAPI {

    @Secured(AuthoritiesAPIAuthorities.READ)
    @GetMapping("/{userId}")
    List<String> getAuthorities();

    @Secured((AuthoritiesAPIAuthorities.GIVE))
    @PostMapping("/{userId}")
    void giveAuthorities();

}
