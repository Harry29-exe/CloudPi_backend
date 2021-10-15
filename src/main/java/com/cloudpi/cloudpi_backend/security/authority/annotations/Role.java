package com.cloudpi.cloudpi_backend.security.authority.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Role {

    /**
     * if user has this role he can give it to other user
     */
    boolean authorityOwnersCanShareIt() default true;

    /**
     * strings with names of permissions
     */
    String[] permissions() default {};

    /**
     * strings with names of role and permission
     */
    String[] mayBeGivenBy() default {};

    /**
     * account types witch gets this role when user is created
     */
    String[] havingItByDefault() default {};

}
