package com.cloudpi.cloudpi_backend.files.physical.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriveDTO {
    private Long id;
    private Long assignedCapacity;
    private Long usedCapacity;
    private Long discId;
}
