package com.cloudpi.cloudpi_backend.files.filesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class DirectoryDto {
    private @NonNull UUID id;
    private @Nullable UUID parentId;
    private @NonNull String dirName;
    private @NonNull String path;
    private @NonNull Long size;

    private @NonNull Date createdAt;
    private @NonNull Date lastModified;
}