package com.cloudpi.cloudpi_backend.utils;

public interface UserValidator {
    boolean validateUsername(String username);
    boolean validateNickname(String nickname);
    boolean validatePassword(String password);
}
