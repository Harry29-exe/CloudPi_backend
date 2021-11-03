package com.cloudpi.cloudpi_backend.files.filesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileStructureDTO {

    private Integer depth;
    private Long size;
    private String rootDirectoryPath;
    private FSDirectoryDTO rootDirectory;
}
