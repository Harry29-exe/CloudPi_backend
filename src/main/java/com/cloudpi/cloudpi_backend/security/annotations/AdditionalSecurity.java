package com.cloudpi.cloudpi_backend.security.annotations;

public @interface AdditionalSecurity {

    boolean checkIfOnlyAccessibleFromLAN() default false;

}
