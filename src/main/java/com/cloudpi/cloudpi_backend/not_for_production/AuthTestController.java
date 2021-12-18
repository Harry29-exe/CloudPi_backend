package com.cloudpi.cloudpi_backend.not_for_production;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthTestController {

    @GetMapping("hello-world")
    @PreAuthorize("isAuthenticated()")
    public String helloWorld() {
        return "Hello World";
    }

}
