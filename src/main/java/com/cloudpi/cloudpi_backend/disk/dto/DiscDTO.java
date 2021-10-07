package com.cloudpi.cloudpi_backend.disk.dto;

import com.cloudpi.cloudpi_backend.disk.pojo.DiscType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscDTO {
    private String discName;
    private DiscType type;
    @Nullable
    private String pathToDrives;
    private Long totalCapacity;
    private Long freeSpace;
    private List<UserDriveDTO> userDiscRoots;
}
