package com.cloudpi.cloudpi_backend.files.structure.dto;

import lombok.Data;

import java.util.List;

@Data
public class FileSystemDto {
    private FsObjectIdDTO id;
    private List<DirectoryDto> childrenDirectories;
    private List<FsObjectIdDTO> childrenFiles;
    private String rootPath;
}
