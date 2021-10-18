package com.cloudpi.cloudpi_backend.user.mappers;

import com.cloudpi.cloudpi_backend.authorities.entities.PermissionEntity;
import com.cloudpi.cloudpi_backend.user.dto.UserDetailsDTO;
import com.cloudpi.cloudpi_backend.user.dto.UserPublicIdDTO;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import com.cloudpi.cloudpi_backend.user.entities.UserDetailsEntity;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import com.cloudpi.cloudpi_backend.user.responses.GetUserResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserWithDetailsDTO userEntityToUserWithDetailsDTO(UserEntity userEntity);



    @Mappings({
            @Mapping(target = "filesInfo", ignore = true),
            @Mapping(target = "filesPermissions", ignore = true)
    })
    UserEntity userDTOToUserEntity(UserWithDetailsDTO userWithDetailsDTO);

    UserPublicIdDTO userEntityToUserPublicIdDTO(UserEntity userEntity);

    UserDetailsDTO userDetailsEntityToDTO(UserDetailsEntity entity);

    UserDetailsEntity userDetailsDTOToEntity(UserDetailsDTO userDetails);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserEntity(@MappingTarget UserDetailsEntity userDetailsEntity, UserDetailsDTO userDetails);

    default GrantedAuthority entityToGrantedAuthority(PermissionEntity entity) {
        return new SimpleGrantedAuthority(entity.getAuthority());
    }
}
