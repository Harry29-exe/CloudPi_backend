package com.cloudpi.cloudpi_backend.user.dto;

import com.cloudpi.cloudpi_backend.user.enpoints.AccountType;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import com.cloudpi.cloudpi_backend.user.mappers.UserMapper;
import lombok.*;

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

    private UserDetailsDTO userDetails;

    public UserEntity toUserEntity() {
        return UserMapper.INSTANCE.userDTOToUserEntity(this);
    }
}