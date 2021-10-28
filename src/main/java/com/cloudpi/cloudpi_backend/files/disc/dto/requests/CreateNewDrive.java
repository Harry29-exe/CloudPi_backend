package com.cloudpi.cloudpi_backend.files.disc.dto.requests;

public record CreateNewDrive(
        Long userId,
        Long sizeInBytes
) {
}
