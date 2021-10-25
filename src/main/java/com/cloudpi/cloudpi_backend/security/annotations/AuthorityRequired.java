package com.cloudpi.cloudpi_backend.security.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("")
public @interface AuthorityRequired {

    String[] authorities() default {};

}
