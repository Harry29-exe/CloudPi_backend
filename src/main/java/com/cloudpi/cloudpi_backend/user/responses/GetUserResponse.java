package com.cloudpi.cloudpi_backend.user.responses;

import com.cloudpi.cloudpi_backend.user.dto.UserPublicIdDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserResponse {
    String nickname;
    String pathToProfilePicture;
    AccountType accountType;

    public static GetUserResponse fromUserPublicId(UserPublicIdDTO user) {
        return new GetUserResponse(
                user.getNickname(),
                user.getPathToProfilePicture(),
                user.getAccountType()
        );
    }
}