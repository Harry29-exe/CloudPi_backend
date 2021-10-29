package com.cloudpi.cloudpi_backend.files.disk.dto.responses;

import com.cloudpi.cloudpi_backend.files.disk.dto.DriveDTO;

import java.util.List;

public record DiscDetails(
        Long discId,
        String discName,
        List<DriveDTO> drivesOnDisc
) {
}
