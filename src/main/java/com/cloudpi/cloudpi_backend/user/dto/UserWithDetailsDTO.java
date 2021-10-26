package com.cloudpi.cloudpi_backend.user.dto;

import com.cloudpi.cloudpi_backend.authorities.dto.AuthorityDTO;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import com.cloudpi.cloudpi_backend.user.mappers.UserMapper;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class UserWithDetailsDTO {
    private Long id;
    private String login;
    private String username;
    private Boolean locked;
    private AccountType accountType;
    private List<AuthorityDTO> roles;
    private List<AuthorityDTO> permissions;

    private UserDetailsDTO userDetails;
}