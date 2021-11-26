package com.cloudpi.cloudpi_backend.user.dto;

import com.cloudpi.cloudpi_backend.authorities.dto.AuthorityDTO;
import com.cloudpi.cloudpi_backend.configuration.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class UserWithDetailsDTO {
    private @Nullable Long id;
    private String username;
    private Boolean locked = false;
    private List<AuthorityDTO> roles = new ArrayList<>();
    private List<AuthorityDTO> permissions = new ArrayList<>();
    private UserDetailsDTO userDetails;

    @Default
    public UserWithDetailsDTO(String username, List<AuthorityDTO> roles, List<AuthorityDTO> permissions, UserDetailsDTO userDetails) {
        this.username = username;
        this.roles = roles;
        this.permissions = permissions;
        this.userDetails = userDetails;
    }

    public UserWithDetailsDTO(String username, UserDetailsDTO userDetails) {
        this.username = username;
        this.userDetails = userDetails;
    }

}