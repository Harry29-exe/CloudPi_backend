package com.cloudpi.cloudpi_backend.files.structure.dto;

import lombok.Data;

@Data
public class FsObjectIdDTO {
    private Long id;
    private String path;
    private Long lastModified;
}
