package com.cloudpi.cloudpi_backend.security.authority_system;

import com.google.common.collect.ImmutableCollection;
import org.springframework.security.core.GrantedAuthority;

public interface AuthorityModel {

    ImmutableCollection<GrantedAuthority> mayBeGivenBy();

    ImmutableCollection<String> getAccountsThatHaveItByDefault();

    String getAuthorityName();

}
