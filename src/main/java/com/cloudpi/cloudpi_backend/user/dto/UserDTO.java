package com.cloudpi.cloudpi_backend.user.dto;

import com.cloudpi.cloudpi_backend.user.controllers.AccountType;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import com.cloudpi.cloudpi_backend.user.mappers.UserMapper;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String nickname;
    private String password;
    private Boolean locked;
    private AccountType accountType;

    public UserEntity toUserEntity() {
        return UserMapper.INSTANCE.userDTOToUserEntity(this);
    }
}