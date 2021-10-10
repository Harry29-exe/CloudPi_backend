package com.cloudpi.cloudpi_backend.files.disk.requests;

public record CreateNewDrive(
        Long userId,
        Long sizeInBytes
) {
}
