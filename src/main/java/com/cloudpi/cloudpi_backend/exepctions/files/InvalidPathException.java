package com.cloudpi.cloudpi_backend.exepctions.files;

public class InvalidPathException extends RuntimeException {

    InvalidPathException(String message) {
        super(message);
    }

    public static InvalidPathException invalidPathFormat() {
        return new InvalidPathException("Given path is not valid path. Path should have following format: username:/directory-1/.../directory-n/filename");
    }

    public static InvalidPathException noSuchUser(String username) {
        return new InvalidPathException("Given path points to user: " + username + " but such user does not exist");
    }

    public static InvalidPathException noSuchDirectory(String existingPartOfPath, String notExistingDirectory) {
        return new InvalidPathException("There is no directory: " + notExistingDirectory + " in: " + existingPartOfPath);
    }

    public static InvalidPathException noSuchFile(String pathToFile, String filename) {
        return new InvalidPathException("There is no file with name: " + filename + " in directory: " + pathToFile);
    }
}
