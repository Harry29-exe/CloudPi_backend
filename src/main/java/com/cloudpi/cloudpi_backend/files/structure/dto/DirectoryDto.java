package com.cloudpi.cloudpi_backend.files.structure.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DirectoryDto {
    private Long id;
    private Long parentId;
    private String fileName;
    private String path;
    private Date createdAt;
    private Date lastModified;
    private Date lastRead;
    private Long childrenSize;
    private List<FileDto> childrenFiles;
    private List<DirectoryDto> childrenDirectories;
}