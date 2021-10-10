package com.cloudpi.cloudpi_backend.files.structure.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FileIdDto {
    private Long fileId;
    private String fileName;
    private Date lastModified;
}