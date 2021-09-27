package com.cloudpi.cloudpi_backend.security.authentication;

public interface JWTService {

    String createJWTToken(String userPrincipal);

    String createRefreshToken(String userPrincipal);

    String refreshJWTToken(String refreshToken);

    String refreshRefreshToken(String refreshToken);

    void validateRefreshToken(String refreshToken);

    void validateJWTToken(String jwtToken);

}
