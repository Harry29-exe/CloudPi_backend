package com.cloudpi.cloudpi_backend.authentication;

import javax.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank String login,
        @NotBlank String password
) {

}
