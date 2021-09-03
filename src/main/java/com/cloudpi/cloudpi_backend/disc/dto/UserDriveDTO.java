package com.cloudpi.cloudpi_backend.disc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDriveDTO {
    private Long id;
    private String ownerUsername;
    private Long assignedCapacity;
    private Long usedCapacity;
}
