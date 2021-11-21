package com.cloudpi.cloudpi_backend.files.filesystem.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class PathInfoDto {

    private final UUID id;
    private final Date modifiedAt;

}
