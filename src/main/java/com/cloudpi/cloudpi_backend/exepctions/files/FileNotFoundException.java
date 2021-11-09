package com.cloudpi.cloudpi_backend.exepctions.files;

import java.util.UUID;

public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException() {
        super("No file with given Id");
    }

    public FileNotFoundException(UUID fileId) {
        super("No file with id: " + fileId.toString());
    }
}
