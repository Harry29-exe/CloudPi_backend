package com.cloudpi.cloudpi_backend.authorization.services;

import com.cloudpi.cloudpi_backend.authorization.dto.AuthorityDTO;
import com.cloudpi.cloudpi_backend.security.CloudPiRole;
import com.cloudpi.cloudpi_backend.security.authority.CPAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface CPAuthorityRepoService {

    Collection<CPAuthority> getUsersAuthorities(String username);

    Collection<CPAuthority> getUsersAuthorities(Long userID);


}
