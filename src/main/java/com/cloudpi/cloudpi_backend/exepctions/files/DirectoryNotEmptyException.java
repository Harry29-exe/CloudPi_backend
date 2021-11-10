package com.cloudpi.cloudpi_backend.exepctions.files;

public class DirectoryNotEmptyException extends RuntimeException {

    public DirectoryNotEmptyException() {
        super("To perform this operation given directory must be empty");
    }
}
