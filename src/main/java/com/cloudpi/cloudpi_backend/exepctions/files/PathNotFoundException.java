package com.cloudpi.cloudpi_backend.exepctions.files;

public class PathNotFoundException extends RuntimeException {

    PathNotFoundException(String message) {
        super(message);
    }

    public static PathNotFoundException noSuchDirectory(String existingPartOfPath, String notExistingDirectory) {
        return new PathNotFoundException("There is no directory: " + notExistingDirectory + " in: " + existingPartOfPath);
    }

    public static PathNotFoundException noSuchDirectory(String path) {
        return new PathNotFoundException("There is no directory with path: " + path);
    }

    public static PathNotFoundException noSuchFile(String pathToFile, String filename) {
        return new PathNotFoundException("There is no file with name: " + filename + " in directory: " + pathToFile);
    }

}
