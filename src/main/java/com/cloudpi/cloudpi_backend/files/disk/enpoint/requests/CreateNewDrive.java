package com.cloudpi.cloudpi_backend.files.disk.enpoint.requests;

public record CreateNewDrive(
        Long userId,
        Long sizeInBytes
) {
}
