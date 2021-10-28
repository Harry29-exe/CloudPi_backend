package com.cloudpi.cloudpi_backend.files.disk.dto.requests;

public record CreateNewDrive(
        Long userId,
        Long sizeInBytes
) {
}
