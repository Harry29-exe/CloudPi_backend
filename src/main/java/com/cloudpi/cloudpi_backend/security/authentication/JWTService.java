package com.cloudpi.cloudpi_backend.security.authentication;

import com.auth0.jwt.interfaces.DecodedJWT;

public interface JWTService {

    String createJWTToken(String userPrincipal);

    String createRefreshToken(String userPrincipal);

    String refreshJWTToken(String refreshToken);

    String refreshRefreshToken(String refreshToken);

    void validateRefreshToken(String refreshToken);

    void validateRefreshToken(DecodedJWT refreshToken);

    void validateJWTToken(String jwtToken);

    void validateJWTToken(DecodedJWT jwtToken);

}
