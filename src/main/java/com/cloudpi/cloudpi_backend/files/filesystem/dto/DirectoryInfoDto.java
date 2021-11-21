package com.cloudpi.cloudpi_backend.files.filesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class DirectoryInfoDto {

    private UUID Id;
    private String name;
    private Long size;
    private Date modifiedAt;
    private Date createdAt;

}
