package com.cloudpi.cloudpi_backend.files.disk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscDTO {
    private String discName;
    private Long totalCapacity;
    private Long freeSpace;
    private List<DriveDTO> discDrives;
}
