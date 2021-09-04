package com.cloudpi.cloudpi_backend.user.mapping;

import com.cloudpi.cloudpi_backend.security.CloudPIUser;
import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

class UserMappingTests {

    private ModelMapper mapper;

    @BeforeEach
    void setUpMapper() {
        mapper = new ModelMapper();

    }

    @Test
    void should_map_user_entity_to_user_dto() {
        mapper.createTypeMap(UserEntity.class, UserDTO.class);
        mapper.validate();
    }

    @Test
    void should_validate_mapping_UserDto_to_UserEntity() {
        mapper.createTypeMap(UserDTO.class, UserEntity.class);
        mapper.validate();
    }

    @Test
    void should_validate_user_entity_to_cloudpi_user() {
        mapper.createTypeMap(UserEntity.class, CloudPIUser.class);
        mapper.validate();
    }
}