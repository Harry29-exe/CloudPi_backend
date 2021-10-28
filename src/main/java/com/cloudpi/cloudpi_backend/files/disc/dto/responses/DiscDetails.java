package com.cloudpi.cloudpi_backend.files.disc.dto.responses;

import com.cloudpi.cloudpi_backend.files.disc.dto.UserDriveDTO;

import java.util.List;

public record DiscDetails(
        Long discId,
        String discName,
        List<UserDriveDTO> drivesOnDisc
) {
}
