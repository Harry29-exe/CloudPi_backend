package com.cloudpi.cloudpi_backend.utils;

public interface RepoService<T, ID> {

    EntityReference<T> getReference(ID entityId);

}
