package com.cloudpi.cloudpi_backend.user.mappers;

import com.cloudpi.cloudpi_backend.authorization.entities.AuthorityPermissionEntity;
import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import com.cloudpi.cloudpi_backend.user.responses.GetUserResponse;
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

    GetUserResponse userDTOToResponse(UserDTO user);

    @Mappings({
            @Mapping(target = "filesInfo", ignore = true),
            @Mapping(target = "filesPermissions", ignore = true)
    })
    UserEntity userDTOToUserEntity(UserDTO userDTO);

    default GrantedAuthority entityToGrantedAuthority(AuthorityPermissionEntity entity) {
        return new SimpleGrantedAuthority(entity.getAuthority());
    }
}
