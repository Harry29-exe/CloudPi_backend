package com.cloudpi.cloudpi_backend.authorization.services;

import com.cloudpi.cloudpi_backend.authorization.dto.AuthorityDTO;

import java.util.Set;

public interface AuthorityRepoService {

    Set<AuthorityDTO> getUsersAuthorities();

}
