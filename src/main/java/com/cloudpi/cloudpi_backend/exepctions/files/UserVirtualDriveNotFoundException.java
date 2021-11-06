package com.cloudpi.cloudpi_backend.exepctions.files;

public class UserVirtualDriveNotFoundException extends RuntimeException {

    public UserVirtualDriveNotFoundException(String username) {
        super("Could not find virtual drive for user: " + username + " it might been deleted by administrator");
    }
}
