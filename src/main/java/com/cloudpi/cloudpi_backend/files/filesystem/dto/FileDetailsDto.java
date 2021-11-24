package com.cloudpi.cloudpi_backend.files.filesystem.dto;

import com.cloudpi.cloudpi_backend.files.filesystem.pojo.FileType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

/**
 * Basically FileDto without fields witch can be obtained
 * based on rest of file structure (e.g. path, parentId)
 */
@Getter
@Setter
public class FileDetailsDto {

    private UUID id;
    private String name;

    private Long size;
    private Date modifiedAt;
    private Date createdAt;
    private FileType fileType;
    private Boolean hasThumbnail;

    public FileDetailsDto(UUID id,
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

