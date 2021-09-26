package com.cloudpi.cloudpi_backend.security.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cloudpi.cloudpi_backend.exepctions.authentication.JWTTokenExpiredException;
import com.cloudpi.cloudpi_backend.security.dto.CloudPIUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.util.Date;

@Service
public class AuthenticationServiceImp implements AuthenticationService {
    private String secret;
    private Algorithm jwtAlgorithm;
    private Long jwtExpireTime;
    private Long refreshExpireTime;

    public AuthenticationServiceImp(
            @Value("${cloud.pi.auth.jwt-secret}") String secret,
            @Value("${cloud.pi.auth.jwt-expire-time}") String jwtExpireTime,
            @Value("${cloud.pi.auth.refresh-expire-time}") String refreshExpireTime) {
        jwtAlgorithm = Algorithm.HMAC256(secret);
        this.jwtExpireTime = (long) Integer.parseInt(jwtExpireTime) * 1000L;
        this.refreshExpireTime = (long) Integer.parseInt(refreshExpireTime) * 1000L;
    }

    @Override
    public String createJWTToken(String userPrincipal) {
        return this.createToken(userPrincipal, TokenType.AUTH);
    }

    @Override
    public String refreshJWTToken(String refreshToken) {
        var decodedToken = JWT.decode(refreshToken);
        this.validateRefreshToken(decodedToken);

        return this.createToken(decodedToken.getClaim("user").asString(), TokenType.AUTH);
    }

    @Override
    public String createRefreshToken(String userPrincipal) {
        return createToken(userPrincipal, TokenType.REFRESH);
    }

    @Override
    public String refreshRefreshToken(String refreshToken) {
        var decodedToken = JWT.decode(refreshToken);
        this.validateRefreshToken(decodedToken);
        return createToken(decodedToken.getClaim("user").asString(), TokenType.REFRESH);
    }

    @Override
    public void validateRefreshToken(String refreshToken) {
        try {
            var jwt = JWT.decode(refreshToken);
            var verifier = createVerifier(
                    jwt.getClaim("user").asString(),
                    jwt.getClaim("type").asString());
            verifier.verify(jwt);
        } catch (JWTVerificationException ex) {
            throw new JWTTokenExpiredException();
        }
    }

    @Override
    public void validateJWTToken(String jwtToken) {
        try {
            var jwt = JWT.decode(jwtToken);
            var verifier = createVerifier(
                    jwt.getClaim("user").asString(),
                    TokenType.AUTH.name());
            verifier.verify(jwt);

        } catch (JWTVerificationException ex) {
            throw new JWTTokenExpiredException();
        }
    }

    private void validateRefreshToken(DecodedJWT refreshToken) {
        try {
            var verifier = createVerifier(
                    refreshToken.getClaim("user").asString(),
                    refreshToken.getClaim("type").asString());
            verifier.verify(refreshToken);
        } catch (JWTVerificationException ex) {
            throw new JWTTokenExpiredException();
        }
    }

    public void validateJWTToken(DecodedJWT jwtToken) {
        try {
            var verifier = createVerifier(
                    jwtToken.getClaim("user").asString(),
                    TokenType.AUTH.name());
            verifier.verify(jwtToken);

        } catch (JWTVerificationException ex) {
            throw new JWTTokenExpiredException();
        }
    }

    private JWTVerifier createVerifier(String userPrincipal, String type) {
        return JWT.require(this.jwtAlgorithm)
                .withClaim("user", userPrincipal)
                .withIssuer("CloudPi")
                .withClaim("type", type)
                .build();
    }

    private String createToken(String userPrincipal, TokenType type) {
        return JWT.create()
                .withClaim("user", userPrincipal)
                .withIssuedAt(new Date())
                .withExpiresAt(
                        new Date(System.currentTimeMillis() +
                        (type == TokenType.AUTH?
                                this.jwtExpireTime:
                                this.refreshExpireTime)
                ))
                .withIssuer("CloudPi")
                .withClaim("type", type.name())
                .sign(jwtAlgorithm);
    }


    private enum TokenType {
        AUTH,
        REFRESH
    }
}
