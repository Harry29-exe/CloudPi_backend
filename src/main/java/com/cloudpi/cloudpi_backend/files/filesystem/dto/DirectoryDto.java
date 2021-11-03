package com.cloudpi.cloudpi_backend.files.filesystem.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DirectoryDto {
    private Long id;
    private Long parentId;
    private String fileName;
    private String path;
    private Long childrenSize;

    private Date createdAt;
    private Date lastModified;

    private List<FileDto> childrenFiles;
    private List<DirectoryDto> childrenDirectories;
}