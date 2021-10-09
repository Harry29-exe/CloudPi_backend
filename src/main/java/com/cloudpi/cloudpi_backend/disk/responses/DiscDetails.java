package com.cloudpi.cloudpi_backend.disk.responses;

import com.cloudpi.cloudpi_backend.disk.dto.UserDriveDTO;

import java.util.List;

public record DiscDetails(
        Long discId,
        String discName,
        List<UserDriveDTO> drivesOnDisc
) {
}
