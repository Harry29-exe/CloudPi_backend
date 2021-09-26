package com.cloudpi.cloudpi_backend.exepctions.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthenticationAdvice {

    @ResponseBody
    @ExceptionHandler(JWTTokenExpiredException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String jwtTokenExpired(JWTTokenExpiredException exception) {
        return exception.getMessage();
    }

}
