package com.cloudpi.cloudpi_backend.files.filesystem.dto;

import com.cloudpi.cloudpi_backend.files.filesystem.pojo.FileType;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class FileDto {
    private UUID id;
    private UUID parentId;
    private String name;
    private FileType fileType;
    private Boolean hasThumbnail;
    private String path;
    private Long size;

    private Date createdAt;
    private Date modifiedAt;

}