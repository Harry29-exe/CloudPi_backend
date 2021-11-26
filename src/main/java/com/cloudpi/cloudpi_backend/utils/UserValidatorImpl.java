package com.cloudpi.cloudpi_backend.utils;

import com.cloudpi.cloudpi_backend.exepctions.user.endpoint.InvalidUserData;
import org.springframework.stereotype.Service;

@Service
public class UserValidatorImpl implements UserValidator {

    private final int usernameMinLength = 3;
    private final int usernameMaxLength = 30;
    private final int nicknameMinLength = 3;
    private final int nicknameMaxLength = 255;

    @Override
    public boolean validateUsername(String username) {
        if(username == null || username.isBlank()) {
            throw new InvalidUserData("Username cannot be null or empty.");
        }

        if(username.contains(" ")) {
            throw new InvalidUserData("Username cannot contain any space.");
        }

        int usernameLength = username.length();

        return usernameLength >= usernameMinLength &&
                usernameLength <= usernameMaxLength &&
                username.chars().allMatch(Character::isLetterOrDigit);
    }

    @Override
    public boolean validateNickname(String nickname) {
        if(nickname == null || nickname.isBlank()) {
            throw new InvalidUserData("Nickname cannot be null or empty");
        }

        int nicknameLength = nickname.length();

        if(nickname.charAt(0) == ' ' || nickname.charAt(nicknameLength - 1) == ' ') {
            throw new InvalidUserData("Nickname cannot contain spaces at the beginning or at the end.");
        } else if(nickname.contains("  ")) {
            throw new InvalidUserData("Nickname cannot contain multiple spaces next to each other.");
        }

        return nicknameLength >= nicknameMinLength &&
                nicknameLength <= nicknameMaxLength &&
                onlyLettersDigitsAndSpacebars(nickname);

    }

    private boolean onlyLettersDigitsAndSpacebars(String nickname) {
        for(char letter : nickname.toCharArray()) {
            if(!Character.isLetterOrDigit(letter) && letter != ' ')
                return false;
        }
        return true;
    }
}
