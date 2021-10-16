package com.cloudpi.cloudpi_backend.user;

import com.cloudpi.cloudpi_backend.user.enpoints.AccountType;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;

import static com.cloudpi.cloudpi_backend.user.UserTestUtils.passwordEncoder;

public final class UserDTOBuilder {
    private Long id = 2L;
    private String username = "steve";
    private String email = "steve@we.com";
    private String nickname = "n steve";
    private String password = passwordEncoder.encode("123");
    private Boolean locked = false;
    private String accountType = AccountType.USER;

    private UserDTOBuilder() {
    }

    public static UserDTOBuilder builder() {
        return new UserDTOBuilder();
    }

    public static UserDTOBuilder fastBuilder(Long id, String username) {
        var temp = new UserDTOBuilder();
        temp.id = id;
        temp.username = username;
        temp.nickname = "n " + username;
        temp.email = username + "@we.com";
        return temp;
    }

    public UserDTOBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserDTOBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserDTOBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserDTOBuilder withNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public UserDTOBuilder withPassword(String password) {
        this.password = passwordEncoder.encode(password);
        return this;
    }


    public UserDTOBuilder withEncodedPassword(String password) {
        this.password = password;
        return this;
    }

    public UserDTOBuilder withLocked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    public UserDTOBuilder withAccountType(String accountType) {
        this.accountType = accountType;
        return this;
    }


    public UserWithDetailsDTO build() {
        UserWithDetailsDTO userWithDetailsDTO = new UserWithDetailsDTO();
        userWithDetailsDTO.setId(id);
        userWithDetailsDTO.setUsername(username);
        userWithDetailsDTO.setEmail(email);
        userWithDetailsDTO.setNickname(nickname);
        userWithDetailsDTO.setPassword(password);
        userWithDetailsDTO.setLocked(locked);
        userWithDetailsDTO.setAccountType(accountType);
        return userWithDetailsDTO;
    }
}
