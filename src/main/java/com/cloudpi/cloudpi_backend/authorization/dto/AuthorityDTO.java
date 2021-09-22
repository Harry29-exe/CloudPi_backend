package com.cloudpi.cloudpi_backend.authorization.dto;

import com.cloudpi.cloudpi_backend.authorization.pojo.AuthorityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityDTO {
    private AuthorityType type;
    private String authority;
}
