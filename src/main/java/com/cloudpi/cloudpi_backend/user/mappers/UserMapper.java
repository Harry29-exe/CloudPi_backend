package com.cloudpi.cloudpi_backend.user.mappers;

import com.cloudpi.cloudpi_backend.authorities.entities.PermissionEntity;
import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import com.cloudpi.cloudpi_backend.user.enpoints.responses.GetUserResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userEntityToUserDTO(UserEntity userEntity);

    GetUserResponse userDTOToResponse(UserDTO user);

    @Mappings({
            @Mapping(target = "filesInfo", ignore = true),
            @Mapping(target = "filesPermissions", ignore = true)
    })
    UserEntity userDTOToUserEntity(UserDTO userDTO);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserEntity(UserDTO userDTO, @MappingTarget UserEntity userEntity);

    default GrantedAuthority entityToGrantedAuthority(PermissionEntity entity) {
        return new SimpleGrantedAuthority(entity.getAuthority());
    }
}
