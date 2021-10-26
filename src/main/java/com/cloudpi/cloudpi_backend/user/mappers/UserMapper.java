package com.cloudpi.cloudpi_backend.user.mappers;

import com.cloudpi.cloudpi_backend.authorities.entities.PermissionEntity;
import com.cloudpi.cloudpi_backend.authorities.entities.RoleEntity;
import com.cloudpi.cloudpi_backend.security.authority_system.AuthorityModelsAggregator;
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

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserWithDetailsDTO userEntityToUserWithDetailsDTO(UserEntity userEntity);

    UserDetailsEntity userDetailsDTOToEntity(UserEntity entity);

    @Mapping(target = "pathToProfilePicture", expression = "java(userEntity.getUserDetails().getPathToProfilePicture())")
    UserPublicIdDTO userEntityToUserPublicIdDTO(UserEntity userEntity);

    UserDetailsDTO userDetailsEntityToDTO(UserDetailsEntity entity);

    UserDetailsEntity userDetailsDTOToEntity(UserDetailsDTO userDetails);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserEntity(@MappingTarget UserDetailsEntity userDetailsEntity, UserDetailsDTO userDetails);

    default GrantedAuthority entityToGrantedAuthority(PermissionEntity entity) {
        return new SimpleGrantedAuthority(entity.getAuthority());
    }


    default Collection<String> permissionToString(Collection<PermissionEntity> entity) {
        return entity.stream().map(PermissionEntity::getAuthority).toList();
    }

    default Collection<String> roleToString(Collection<RoleEntity> entity) {
        return entity.stream().map(RoleEntity::getRole).toList();
    }
}
