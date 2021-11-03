package com.cloudpi.cloudpi_backend.files.filesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FSDirectoryDTO {

    private Integer id;
    private String name;
    private Long lastFileModification;
    private List<FSFileDTO> files;
    private List<FSDirectoryDTO> directories;

}
