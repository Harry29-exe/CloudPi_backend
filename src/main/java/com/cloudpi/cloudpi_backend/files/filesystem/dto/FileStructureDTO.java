package com.cloudpi.cloudpi_backend.files.filesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class FileStructureDTO {

    private @NonNull Integer depth;
    private @NonNull String rootDirectoryPath;
    private @NonNull FSDirectoryDTO rootDirectory;

    @Data
    public static class FSFileDTO {
        private @NonNull UUID id;
        private @NonNull FileInfoDto detail;

        public FSFileDTO(@NonNull UUID id, @NonNull FileInfoDto detail) {
            this.id = id;
            this.detail = detail;
        }
    }

    @Data
    public static class FSDirectoryDTO {
        private @NonNull UUID id;
        private @NonNull DirectoryInfoDto details;
        private @NonNull List<FSFileDTO> files = new ArrayList<>();
        private @NonNull List<FSDirectoryDTO> directories  = new ArrayList<>();

        public FSDirectoryDTO(@NonNull UUID id) {
            this.id = id;
        }
    }

}
