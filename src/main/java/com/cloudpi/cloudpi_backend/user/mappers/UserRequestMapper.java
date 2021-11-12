package com.cloudpi.cloudpi_backend.user.mappers;

import com.cloudpi.cloudpi_backend.user.dto.UserDetailsDTO;
import com.cloudpi.cloudpi_backend.user.dto.UserPublicIdDTO;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import com.cloudpi.cloudpi_backend.user.requests.UpdateUserDetailsRequest;
import com.cloudpi.cloudpi_backend.user.responses.GetUserResponse;
import com.cloudpi.cloudpi_backend.user.responses.GetUserWithDetailsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserRequestMapper {

    UserRequestMapper INSTANCE = Mappers.getMapper(UserRequestMapper.class);


    /**
     * to requests
     */
    GetUserResponse userDTOToResponse(UserPublicIdDTO user);

    @Mappings({
            @Mapping(target = "usersRoles", expression = "java(userDTO.getRoles().stream().map(com.cloudpi.cloudpi_backend.authorities.dto.AuthorityDTO::authority).toList())"),
            @Mapping(target = "usersPermissions", expression = "java(userDTO.getPermissions().stream().map(com.cloudpi.cloudpi_backend.authorities.dto.AuthorityDTO::authority).toList())"),
            @Mapping(target = "email", expression = "java(userDTO.getUserDetails().getEmail())"),
            @Mapping(target = "isLocked", source = "locked")
    })
    GetUserWithDetailsResponse userWithDetailsDTOToResponse(UserWithDetailsDTO userDTO);


    /**
     * to DTOs
     */

    @Mapping(target = "pathToProfilePicture", expression = "java(request.getPathToProfilePicture())")
    UserDetailsDTO userDetailsRequestToDTO(UpdateUserDetailsRequest request);
}
