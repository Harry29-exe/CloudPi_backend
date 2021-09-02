package com.cloudpi.cloudpi_backend.files_info.dto;

import lombok.Data;

@Data
public class FsObjectIdDTO {
    private Long id;
    private String path;
    private Long lastModified;
}
