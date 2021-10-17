package com.cloudpi.cloudpi_backend.user.dto;

import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import com.cloudpi.cloudpi_backend.user.mappers.UserMapper;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class UserWithDetailsDTO {
    private Long id;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private Boolean locked;
    private String accountType;

    public UserEntity toUserEntity() {
        return UserMapper.INSTANCE.userDTOToUserEntity(this);
    }
}