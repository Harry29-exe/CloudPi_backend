package com.cloudpi.cloudpi_backend.user.mappers;

import com.cloudpi.cloudpi_backend.user.dto.UserDetailsDTO;
import com.cloudpi.cloudpi_backend.user.dto.UserPublicIdDTO;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import com.cloudpi.cloudpi_backend.user.requests.PostUserRequest;
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

    GetUserWithDetailsResponse userWithDetailsDTOToResponse(UserWithDetailsDTO userDTO);


    //TODO mapping is incorrect (does not map data from request to UserDetails)
    /**
     * to DTOs
     */
    @Mappings( {
            @Mapping(target = "locked", constant = "false")

    })
    UserWithDetailsDTO userCreateRequestToUserWithDetailsDTO(PostUserRequest request);

    UserDetailsDTO userDetailsRequestToDTO(UpdateUserDetailsRequest request);
}
