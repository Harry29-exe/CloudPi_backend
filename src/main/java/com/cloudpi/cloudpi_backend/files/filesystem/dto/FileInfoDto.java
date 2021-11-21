package com.cloudpi.cloudpi_backend.files.filesystem.dto;

import com.cloudpi.cloudpi_backend.files.filesystem.pojo.FileType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class FileInfoDto {

    private UUID id;
    private String name;
    private Long size;
    private Date modifiedAt;
    private Date createdAt;
    private FileType fileType;
    private Boolean hasThumbnail;

    public FileInfoDto(UUID id,
                       String name,
                       Long size,
                       Date modifiedAt,
                       Date createdAt,
                       FileType fileType,
                       Boolean hasThumbnail) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.modifiedAt = modifiedAt;
        this.createdAt = createdAt;
        this.fileType = fileType;
        this.hasThumbnail = hasThumbnail;
    }
}

