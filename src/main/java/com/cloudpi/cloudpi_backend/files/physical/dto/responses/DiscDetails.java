package com.cloudpi.cloudpi_backend.files.physical.dto.responses;

import com.cloudpi.cloudpi_backend.files.physical.dto.DriveDTO;

import java.util.List;

public record DiscDetails(
        Long discId,
        String discName,
        List<DriveDTO> drivesOnDisc
) {
}
