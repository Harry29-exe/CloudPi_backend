package com.cloudpi.cloudpi_backend.files.filesystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class FsDirectoryDTO {
    private FsObjectIdDTO id;
    private List<FsDirectoryDTO> childrenDirectories;
    private List<FsObjectIdDTO> childrenFiles;
}
