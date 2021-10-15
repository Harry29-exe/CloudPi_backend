package com.cloudpi.cloudpi_backend.authorities.endpoint;

import com.cloudpi.cloudpi_backend.authorities.endpoint.responses.GetAuthoritiesInfoResponse;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/authorities")
public interface AuthoritiesAPI {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{username}/canBeGiven")
    GetAuthoritiesInfoResponse getAvailableAuthorities(Authentication auth);

    @Secured(AuthoritiesAPIAuthorities.READ)
    @GetMapping("/{username}")
    List<String> getAuthorities(@Param("username") String username);

    @Secured((AuthoritiesAPIAuthorities.GIVE))
    @PostMapping("/{userId}")
    void giveAuthorities();

}
