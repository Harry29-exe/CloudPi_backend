package com.cloudpi.cloudpi_backend.files.filesystem.dto;

import com.cloudpi.cloudpi_backend.files.filesystem.pojo.FileType;
import lombok.Data;

import java.util.Date;

@Data
public class FileDto {
    private Long id;
    private Long parentId;
    private String fileName;
    private FileType fileType;
    private String path;
    private Long size;

    private Date createdAt;
    private Date modifiedAt;

}