package com.cloudpi.cloudpi_backend.user;

import com.cloudpi.cloudpi_backend.security.permissions.AccountType;
import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

import static com.cloudpi.cloudpi_backend.user.UserTestUtils.passwordEncoder;

public final class UserDTOBuilder {
    private Long id = 2L;
    private String username = "steve";
    private String email = "steve@we.com";
    private String nickname = "n steve";
    private String password = passwordEncoder.encode("123");
    private Boolean locked = false;
    private AccountType accountType = AccountType.USER;
    private List<GrantedAuthority> permissions = new ArrayList<>();

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

    public UserDTOBuilder withAccountType(AccountType accountType) {
        this.accountType = accountType;
        return this;
    }

    public UserDTOBuilder withPermissions(List<GrantedAuthority> permissions) {
        this.permissions = permissions;
        return this;
    }

    public UserDTO build() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setUsername(username);
        userDTO.setEmail(email);
        userDTO.setNickname(nickname);
        userDTO.setPassword(password);
        userDTO.setLocked(locked);
        userDTO.setAccountType(accountType);
        userDTO.setPermissions(permissions);
        return userDTO;
    }
}
