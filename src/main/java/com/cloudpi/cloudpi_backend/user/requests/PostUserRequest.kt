package com.cloudpi.cloudpi_backend.user.requests;

import lombok.Data;

@Data
public class PostUserRequest {
    private String username;
    private String nickname;
    private String password;
}