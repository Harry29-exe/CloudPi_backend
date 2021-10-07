package com.cloudpi.cloudpi_backend.security.authority;

import com.google.common.collect.ImmutableCollection;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface AuthorityModel {

    ImmutableCollection<GrantedAuthority> mayBeGivenBy();

    String getName();

}
