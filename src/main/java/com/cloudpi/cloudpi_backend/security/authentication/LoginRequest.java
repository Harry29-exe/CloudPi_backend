package com.cloudpi.cloudpi_backend.security.authentication;

import javax.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank String username,
        @NotBlank String password
) {

}
