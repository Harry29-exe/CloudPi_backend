package com.cloudpi.cloudpi_backend.files.filesystem.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FileIdDto {
    private Long fileId;
    private String fileName;
    private Date lastModified;
}