package com.cloudpi.cloudpi_backend.security.filters;

import com.auth0.jwt.JWT;
import com.cloudpi.cloudpi_backend.authentication.CPUserDetailsService;
import com.cloudpi.cloudpi_backend.authentication.JWTService;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final CPUserDetailsService userDetailsService;
    private final JWTService jwtService;

    public JWTAuthenticationFilter(CPUserDetailsService userDetailsService, JWTService jwtService) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }
        var decodedToken = JWT.decode(authHeader);
        jwtService.validateAccessToken(decodedToken);

        var userDetails = userDetailsService.loadUserByUsername(decodedToken.getClaim("sub").asString());
        var authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
//        authentication.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

}
