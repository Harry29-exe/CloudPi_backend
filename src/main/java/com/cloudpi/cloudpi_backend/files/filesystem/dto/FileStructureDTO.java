package com.cloudpi.cloudpi_backend.files.filesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class FileStructureDTO {

    private Integer depth;
    private String rootDirectoryPath;
    private FSDirectoryDTO rootDirectory;

    @Data
    public static class FSFileDTO {
        private UUID id;
        private FileInfoDto details;

        public FSFileDTO(UUID id, FileInfoDto details) {
            this.id = id;
            this.details = details;
        }
    }

    @Data
    public static class FSDirectoryDTO {
        private UUID id;
        private DirectoryInfoDto details;
        private List<FSFileDTO> files = new ArrayList<>();
        private List<FSDirectoryDTO> directories  = new ArrayList<>();

        public FSDirectoryDTO(UUID id) {
            this.id = id;
        }
    }

}
