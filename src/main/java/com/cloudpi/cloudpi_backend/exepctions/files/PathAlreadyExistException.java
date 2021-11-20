package com.cloudpi.cloudpi_backend.exepctions.files;

public class PathAlreadyExistException extends RuntimeException {

    public PathAlreadyExistException(String path) {
        super("There already exist object with path: " + path);
    }
}
