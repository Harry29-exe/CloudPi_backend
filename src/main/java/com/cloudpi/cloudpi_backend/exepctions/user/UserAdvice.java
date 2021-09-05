package com.cloudpi.cloudpi_backend.exepctions.user;

import com.cloudpi.cloudpi_backend.exepctions.user.endpoint.UsernameIsTakenException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserAdvice {

    @ResponseBody
    @ExceptionHandler(UsernameIsTakenException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String usernameIsTakenAdvice(UsernameIsTakenException ex) {
        return ex.getMessage();
    }

}
