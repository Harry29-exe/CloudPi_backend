package com.cloudpi.cloudpi_backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetailsDTO {

    private String nickname;
    private String email;
    private String pathToPhoto;

}
