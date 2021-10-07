package com.cloudpi.cloudpi_backend.security.authentication;

import com.cloudpi.cloudpi_backend.exepctions.authorization.NoRefreshTokenException;
import com.cloudpi.cloudpi_backend.user.services.CloudPiUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/")
public class LoginController {
    private final JWTService jwtService;
    private final AuthenticationManager authManager;
    private final CloudPiUserDetailsService userDetailsService;

    public LoginController(JWTService jwtService, AuthenticationManager authManager, CloudPiUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("login")
    public String login(@Valid LoginRequest request, HttpServletResponse httpResponse) {
        var userDetails = userDetailsService.loadUserByUsername(request.username());
        if(! (userDetails.isAccountNonLocked())) {
            Logger.getGlobal().log(new LogRecord(Level.WARNING, "Account is locked"));
//            throw new
        }
        var auth = new UsernamePasswordAuthenticationToken(request.username(), request.password(), userDetails.getAuthorities());
        authManager.authenticate(auth);

        httpResponse.addCookie(createRefreshTokenCookie(
                        jwtService.createRefreshToken(auth.getPrincipal().toString())
        ));

        return jwtService.createJWTToken(auth.getPrincipal().toString());
    }

    @PostMapping("refresh/auth")
    public String refreshAuthToken(HttpServletRequest httpRequest) {
        var refreshToken = getRefreshToken(httpRequest.getCookies());

        return this.jwtService.refreshJWTToken(refreshToken);
    }

    @PostMapping("refresh/refresh")
    public void refreshRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        var refreshToken = getRefreshToken(request.getCookies());

        response.addCookie(createRefreshTokenCookie(
                jwtService.refreshRefreshToken(refreshToken)
        ));
    }

    private String getRefreshToken(Cookie[] cookies) {
        String refreshToken = null;
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals("refresh-token")) {
                refreshToken = cookie.getValue();
                break;
            }
        }

        if(refreshToken == null) {
            throw new NoRefreshTokenException();
        }
        return refreshToken;
    }

    private Cookie createRefreshTokenCookie(String refreshToken) {
        var cookie = new Cookie(
                "refresh-token",
                refreshToken
        );
        cookie.setSecure(true);

        return cookie;
    }

}
