package com.cloudpi.cloudpi_backend.files.filesystem.enpoints;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileStructureDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.responses.GetUserDriveInfo;

import java.util.List;

public interface FilesystemApiDocs extends FilesystemAPI {

    @Override
    default FileStructureDTO getPartOfUsersFileStructure(String username, Integer structureLevels, String fileStructureRoot) {
        return null;
    }

    @Override
    default FileDto getFileInfo(Long fileId, Boolean withPermissions) {
        return null;
    }

    @Override
    default List<GetUserDriveInfo> getUsersDrivesInfo() {
        return null;
    }

    @Override
    default void changeDriveMaxSize(Long newAssignedSpace) {

    }
}
