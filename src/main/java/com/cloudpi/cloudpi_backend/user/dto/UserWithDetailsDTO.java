package com.cloudpi.cloudpi_backend.user.dto;

import com.cloudpi.cloudpi_backend.authorities.dto.AuthorityDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class UserWithDetailsDTO {
    private @Nullable Long id;
    private String login;
    private String username;
    private Boolean locked = false;
    private AccountType accountType = AccountType.USER;
    private List<AuthorityDTO> roles = new ArrayList<>();
    private List<AuthorityDTO> permissions = new ArrayList<>();

    private UserDetailsDTO userDetails;

    public UserWithDetailsDTO(String login, String username, List<AuthorityDTO> roles, List<AuthorityDTO> permissions, UserDetailsDTO userDetails) {
        this.login = login;
        this.username = username;
        this.roles = roles;
        this.permissions = permissions;
        this.userDetails = userDetails;
    }

    public UserWithDetailsDTO(String login, String username, UserDetailsDTO userDetails) {
        this.login = login;
        this.username = username;
        this.userDetails = userDetails;
    }


    public UserWithDetailsDTO(String login, String username) {
        this.login = login;
        this.username = username;
        this.userDetails = new UserDetailsDTO(null, null);
    }

}