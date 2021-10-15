package com.cloudpi.cloudpi_backend.security.authentication;

import com.auth0.jwt.interfaces.DecodedJWT;

public interface JWTService {

    String createAccessToken(String userPrincipal);

    String createRefreshToken(String userPrincipal);

    String refreshAccessToken(String refreshToken);

    String refreshRefreshToken(String refreshToken);

    void validateRefreshToken(String refreshToken);

    void validateRefreshToken(DecodedJWT refreshToken);

    void validateAccessToken(String jwtToken);

    void validateAccessToken(DecodedJWT jwtToken);

}
