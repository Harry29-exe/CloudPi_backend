package com.cloudpi.cloudpi_backend.files.filesystem.dto;

import com.cloudpi.cloudpi_backend.files.filesystem.pojo.FileType;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;
import java.util.UUID;

/**
 * Mapping of FileEntity to DTO
 */
@Data
public class FileDto implements PathObjectDto {
    private @NonNull UUID id;
    private @NonNull UUID parentId;
    private @NonNull String name;
    private @NonNull String path;

    private @NonNull Long size;
    private @NonNull Date createdAt;
    private @NonNull Date modifiedAt;

    private @NonNull FileType fileType;
    private @NonNull Boolean hasThumbnail;

}