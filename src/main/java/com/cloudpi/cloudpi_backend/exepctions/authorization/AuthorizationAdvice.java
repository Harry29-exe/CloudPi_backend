package com.cloudpi.cloudpi_backend.exepctions.authorization;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthorizationAdvice {

    @ResponseBody
    @ExceptionHandler(PermissionNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String noRightPermission(PermissionNotFoundException ex) {
        return ex.getMessage();
    }
}
