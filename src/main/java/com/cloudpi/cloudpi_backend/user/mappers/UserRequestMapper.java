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


    /**
     * to DTOs
     */

    default UserWithDetailsDTO userCreateRequestToUserWithDetailsDTO(PostUserRequest request) {
        return new UserWithDetailsDTO(null, request.getUsername(), false, request.getAccountType(),
                new UserDetailsDTO(request.getNickname(), request.getEmail(), null));
    }

    UserDetailsDTO userDetailsRequestToDTO(UpdateUserDetailsRequest request);
}
