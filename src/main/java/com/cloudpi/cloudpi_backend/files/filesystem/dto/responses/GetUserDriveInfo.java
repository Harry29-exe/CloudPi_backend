package com.cloudpi.cloudpi_backend.files.filesystem.dto.responses;

import lombok.Data;
import lombok.NonNull;

@Data
public class GetUserDriveInfo {

    @NonNull
    private String username;
    @NonNull
    private Long assignedSpace;
    @NonNull
    private Long usedSpace;

}
