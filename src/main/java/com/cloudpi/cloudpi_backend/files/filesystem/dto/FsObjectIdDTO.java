package com.cloudpi.cloudpi_backend.files.filesystem.dto;

import lombok.Data;

@Data
public class FsObjectIdDTO {
    private Long id;
    private String path;
    private Long lastModified;
}
