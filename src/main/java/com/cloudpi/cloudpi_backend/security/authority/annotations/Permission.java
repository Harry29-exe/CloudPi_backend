package com.cloudpi.cloudpi_backend.security.authority.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {

    //strings with names of role and permission
    String[] mayBeGivenBy() default {};

    //account types witch gets this role when user is created
    String[] havingItByDefault() default {};

}
