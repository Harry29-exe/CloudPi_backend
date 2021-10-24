package com.cloudpi.cloudpi_backend.authentication;

import com.cloudpi.cloudpi_backend.exepctions.authorization.NoRefreshTokenException;
import com.cloudpi.cloudpi_backend.user.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping
public class LoginController {
    private final JWTService jwtService;
    private final AuthenticationManager authManager;
    private final CPUserDetailsServiceImp userDetailsService;
    private final UserService userService;

    public LoginController(JWTService jwtService, AuthenticationManager authManager, CPUserDetailsServiceImp userDetailsService, UserService userService) {
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @PostMapping(value = "login", consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public void login(@RequestBody @Valid LoginRequest request, HttpServletResponse httpResponse) {
        var username = userDetailsService.findUsernameByLogin(request.login());
        var userDetails = userDetailsService.loadUserByUsername(username);
        if (!(userDetails.isAccountNonLocked())) {
            Logger.getGlobal().log(new LogRecord(Level.WARNING, "Account is locked"));
//            throw new
        }
        var auth = new UsernamePasswordAuthenticationToken(username, request.password(), userDetails.getAuthorities());
        authManager.authenticate(auth);

        httpResponse.addCookie(createRefreshTokenCookie(
                jwtService.createRefreshToken(auth.getPrincipal().toString())
        ));

        httpResponse.addHeader("Authorization", jwtService.createAccessToken(auth.getPrincipal().toString()));
    }

    @PostMapping("refresh/auth")
    public void refreshAccessToken(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        var refreshToken = getRefreshToken(httpRequest.getCookies());

        httpResponse.setHeader("Authorization", jwtService.refreshAccessToken(refreshToken));
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
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh-token")) {
                refreshToken = cookie.getValue();
                break;
            }
        }

        if (refreshToken == null) {
            throw new NoRefreshTokenException();
        }
        return refreshToken;
    }

    private Cookie createRefreshTokenCookie(String refreshToken) {
        var cookie = new Cookie(
                "refresh-token",
                refreshToken
        );

        cookie.setHttpOnly(true);

        //TODO for development only!!
        cookie.setSecure(false);

        return cookie;
    }

}
