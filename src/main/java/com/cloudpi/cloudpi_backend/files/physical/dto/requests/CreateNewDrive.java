package com.cloudpi.cloudpi_backend.files.physical.dto.requests;

public record CreateNewDrive(
        Long userId,
        Long sizeInBytes
) {
}
