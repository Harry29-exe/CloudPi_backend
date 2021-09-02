package com.cloudpi.cloudpi_backend.files_info.dto;

import lombok.Data;
import java.util.*;

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