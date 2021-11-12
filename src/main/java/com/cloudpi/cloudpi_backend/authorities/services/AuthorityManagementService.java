package com.cloudpi.cloudpi_backend.authorities.services;

import com.cloudpi.cloudpi_backend.authorities.dto.AuthorityDTO;

import java.util.Collection;

public interface AuthorityManagementService {

    //todo
//    @PreAuthorize("")
    void giveUserAuthority(String nickname, AuthorityDTO authority);

    void giveUserAuthorities(String nickname, Collection<AuthorityDTO> authorities);

    void removeUserAuthority(String nickname, AuthorityDTO authority);

    void removeUserAuthorities(String nickname, Collection<AuthorityDTO> authority);

}
