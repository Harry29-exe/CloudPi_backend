package com.cloudpi.cloudpi_backend.authorities.dto;

import com.cloudpi.cloudpi_backend.authorities.pojo.AuthorityType;
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
