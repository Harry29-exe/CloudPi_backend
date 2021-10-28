package com.cloudpi.cloudpi_backend.files.filesystem.enpoints;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileInfoAPIController implements FileInfoAPI {

    @Override
    public DirectoryDto getUsersFileStructure(String username) {
        return null;
    }

    @Override
    public DirectoryDto getPartOfUsersFileStructure(String username, Integer structureLevels, String fileStructureRoot) {
        return null;
    }

    @Override
    public FileDto getFileInfo(Long fileId, Boolean withPermissions) {
        return null;
    }
}
