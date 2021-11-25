package com.cloudpi.cloudpi_backend.configuration.springdoc;

import io.swagger.v3.oas.annotations.Operation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class NotImplemented {

    @Operation(
            summary = SpringDocMessages.notImplemented,
            description = SpringDocMessages.methodNotImplemented1
    )
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface LOW {
    }

    @Operation(
            summary = SpringDocMessages.notImplemented,
            description = SpringDocMessages.methodNotImplemented2
    )
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MEDIUM {
    }

    @Operation(
            summary = SpringDocMessages.notImplemented,
            description = SpringDocMessages.methodNotImplemented1
    )
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface HIGH {
    }

}
