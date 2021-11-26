package com.cloudpi.cloudpi_backend.files.filesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

/**
 * Basically DirectoryDto without fields witch can be obtained
 * based on rest of file structure (e.g. path, parentId)
 */
@Data
@AllArgsConstructor
public class DirectoryDetailsDto {

    private UUID Id;
    private String name;

    private Long size;
    private Date modifiedAt;
    private Date createdAt;

}
