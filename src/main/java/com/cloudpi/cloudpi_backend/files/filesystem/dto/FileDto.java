package com.cloudpi.cloudpi_backend.files.filesystem.dto;

import com.cloudpi.cloudpi_backend.files.filesystem.pojo.FileType;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class FileDto {
    private UUID id;
    private UUID parentId;
    private Long driveId;
    private String fileName;
    private FileType fileType;
    private String path;
    private Long size;

    private Date createdAt;
    private Date modifiedAt;

}