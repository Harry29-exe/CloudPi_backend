package com.cloudpi.cloudpi_backend.user.mappers;

import com.cloudpi.cloudpi_backend.security.CloudPiAuthentication;
import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import com.cloudpi.cloudpi_backend.user.entities.UserGrantedAuthorityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userEntityToUserDTO(UserEntity userEntity);

    @Mappings({
            @Mapping(target = "filesInfo", ignore = true),
            @Mapping(target = "filesPermissions", ignore = true)
    })
    UserEntity userDTOToUserEntity(UserDTO userDTO);

    default GrantedAuthority entityToGrantedAuthority(UserGrantedAuthorityEntity entity) {
        return new SimpleGrantedAuthority(entity.getAuthority());
    }
}
