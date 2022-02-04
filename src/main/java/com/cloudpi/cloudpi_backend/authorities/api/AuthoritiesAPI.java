package com.cloudpi.cloudpi_backend.authorities.api;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/authorities")
public interface AuthoritiesAPI {

//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/{username}/canBeGiven")
//    GetAuthoritiesInfoResponse getAvailableAuthorities(Authentication auth);

    @GetMapping("/{login}")
    List<String> getAuthorities(@Param("login") String username);

    @PostMapping("/{userId}")
    void giveAuthorities();

}
