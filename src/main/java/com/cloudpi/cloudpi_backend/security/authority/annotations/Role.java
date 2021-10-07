package com.cloudpi.cloudpi_backend.security.authority.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Role {

    //strings with names of permissions
    String[] permissions() default {};

    //strings with names of role and permission
    String[] mayBeGivenBy() default {};

}
