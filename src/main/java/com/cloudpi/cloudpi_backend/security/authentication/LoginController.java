package com.cloudpi.cloudpi_backend.security.authentication;

import com.cloudpi.cloudpi_backend.security.CloudPIUserDetailsService;
import com.cloudpi.cloudpi_backend.security.dto.CloudPiAuthentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.http.HttpResponse;

@RestController("/api/")
public class LoginController {
    private AuthenticationService authService;
    private AuthenticationManager authManager;
    private CloudPIUserDetailsService userDetailsService;

    public LoginController(AuthenticationService authService, AuthenticationManager authManager, CloudPIUserDetailsService userDetailsService) {
        this.authService = authService;
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("login")
    public String login(@Valid LoginRequest request, HttpServletResponse httpResponse) {
        var auth = new CloudPiAuthentication(userDetailsService.loadUserByUsername(request.username()));
        authManager.authenticate(auth);


        httpResponse.addCookie(new Cookie(
                "refresh-token",
                authService.createRefreshToken(auth.getPrincipal())));

        return authService.createJWTToken(auth.getPrincipal());
    }

}
