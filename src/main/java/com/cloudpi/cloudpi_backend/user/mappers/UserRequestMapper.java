package com.cloudpi.cloudpi_backend.user.mappers;

import com.cloudpi.cloudpi_backend.user.dto.UserPublicIdDTO;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import com.cloudpi.cloudpi_backend.user.requests.PostUserRequest;
import com.cloudpi.cloudpi_backend.user.responses.GetUserResponse;
import com.cloudpi.cloudpi_backend.user.responses.GetUserWithDetailsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserRequestMapper {

    UserRequestMapper INSTANCE = Mappers.getMapper(UserRequestMapper.class);

    GetUserResponse userDTOToResponse(UserPublicIdDTO user);

    @Mappings( {
        @Mapping(target = "locked", constant = "false")
    })
    UserWithDetailsDTO userCreateRequestToUserWithDetailsDTO(PostUserRequest request);

    GetUserWithDetailsResponse userWithDetailsDTOToResponse(UserWithDetailsDTO userDTO);
}
