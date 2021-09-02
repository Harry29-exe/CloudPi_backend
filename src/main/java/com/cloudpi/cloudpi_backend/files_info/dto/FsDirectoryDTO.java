package com.cloudpi.cloudpi_backend.files_info.dto;

import lombok.Data;

import java.util.List;

@Data
public class FsDirectoryDTO {
    private FsObjectIdDTO id;
    private List<FsDirectoryDTO> childrenDirectories;
    private List<FsObjectIdDTO> childrenFiles;
}
