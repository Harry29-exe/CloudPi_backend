package com.cloudpi.cloudpi_backend.files.filesystem.dto;

import com.cloudpi.cloudpi_backend.files.filesystem.pojo.FileType;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

public record CreateFileDTO(
        @NonNull VirtualPath path,
        @NonNull Long size,
        @Nullable FileType fileType
) {
}
