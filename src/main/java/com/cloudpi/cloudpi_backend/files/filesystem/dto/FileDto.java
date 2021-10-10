package com.cloudpi.cloudpi_backend.files.filesystem.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FileDto {
    private Long id;
    private Long parentId;
    private String fileName;
    private String path;
    private Date createdAt;
    private Date lastModified;
    private Date lastRead;
    private Long size;
}