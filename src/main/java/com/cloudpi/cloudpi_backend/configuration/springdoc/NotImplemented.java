package com.cloudpi.cloudpi_backend.configuration.springdoc;

import io.swagger.v3.oas.annotations.Operation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Operation(
        summary = SpringDocMessages.notImplemented,
        description = SpringDocMessages.methodNotImplemented1
)
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotImplemented {
}
