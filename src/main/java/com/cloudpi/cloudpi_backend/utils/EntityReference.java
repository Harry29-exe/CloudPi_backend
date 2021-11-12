package com.cloudpi.cloudpi_backend.utils;

public class EntityReference<T> {
    private final T entityReference;

    public EntityReference(T entityReference) {
        this.entityReference = entityReference;
    }

    public static <TYPE> EntityReference<TYPE> of(TYPE reference) {
        return new EntityReference<>(reference);
    }

    public T getReference() {
        return entityReference;
    }
}
