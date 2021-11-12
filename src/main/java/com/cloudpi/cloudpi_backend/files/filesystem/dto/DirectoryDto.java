package com.cloudpi.cloudpi_backend.files.filesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class DirectoryDto {
    private UUID id;
    private UUID parentId;
    private String dirName;
    private String path;
    private Long childrenSize;

    private Date createdAt;
    private Date lastModified;
}