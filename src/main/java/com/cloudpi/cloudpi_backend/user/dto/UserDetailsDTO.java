package com.cloudpi.cloudpi_backend.user.dto;

import com.cloudpi.cloudpi_backend.user.entities.UserDetailsEntity;
import com.cloudpi.cloudpi_backend.user.mappers.UserMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetailsDTO {

    private String email;
    private String pathToProfilePicture;

    public UserDetailsEntity toEntity() {
        return UserMapper.INSTANCE.userDetailsDTOToEntity(this);
    }
}
