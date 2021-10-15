package com.cloudpi.cloudpi_backend.security.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cloudpi.cloudpi_backend.exepctions.authentication.JWTTokenExpiredException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTServiceImp implements JWTService {
    private String secret;
    private final Algorithm jwtAlgorithm;
    private final Long accessTokenExpireTime;
    private final Long refreshExpireTime;

    public JWTServiceImp(
            @Value("${cloud.pi.auth.jwt-secret}") String secret,
            @Value("${cloud.pi.auth.jwt-expire-time}") String accessTokenExpireTime,
            @Value("${cloud.pi.auth.refresh-expire-time}") String refreshExpireTime) {
        jwtAlgorithm = Algorithm.HMAC256(secret);
        this.accessTokenExpireTime = (long) Integer.parseInt(accessTokenExpireTime) * 1000L;
        this.refreshExpireTime = (long) Integer.parseInt(refreshExpireTime) * 1000L;
    }

    @Override
    public String createAccessToken(String userPrincipal) {
        return this.createToken(userPrincipal, TokenType.ACCESS);
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        var decodedToken = JWT.decode(refreshToken);
        this.validateRefreshToken(decodedToken);

        return this.createToken(decodedToken.getClaim("user").asString(), TokenType.ACCESS);
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
    public void validateAccessToken(String jwtToken) {
        try {
            var jwt = JWT.decode(jwtToken);
            var verifier = createVerifier(
                    jwt.getClaim("user").asString(),
                    TokenType.ACCESS.name());
            verifier.verify(jwt);

        } catch (JWTVerificationException ex) {
            throw new JWTTokenExpiredException();
        }
    }

    @Override
    public void validateRefreshToken(DecodedJWT refreshToken) {
        try {
            var verifier = createVerifier(
                    refreshToken.getClaim("user").asString(),
                    refreshToken.getClaim("type").asString());
            verifier.verify(refreshToken);
        } catch (JWTVerificationException ex) {
            throw new JWTTokenExpiredException();
        }
    }

    @Override
    public void validateAccessToken(DecodedJWT jwtToken) {
        try {
            var verifier = createVerifier(
                    jwtToken.getClaim("user").asString(),
                    TokenType.ACCESS.name());
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
                                (type == TokenType.ACCESS ?
                                        this.accessTokenExpireTime :
                                        this.refreshExpireTime)
                        ))
                .withIssuer("CloudPi")
                .withClaim("type", type.name())
                .sign(jwtAlgorithm);
    }


    private enum TokenType {
        ACCESS,
        REFRESH
    }
}
