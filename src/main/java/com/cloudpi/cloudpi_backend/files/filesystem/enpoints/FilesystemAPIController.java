package com.cloudpi.cloudpi_backend.files.filesystem.enpoints;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.responses.GetUserDriveInfo;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FilesystemAPIController implements FilesystemAPI {

    @Override
    public DirectoryDto getPartOfUsersFileStructure(String username, Integer structureLevels, String fileStructureRoot) {
        return null;
    }

    @Override
    public FileDto getFileInfo(Long fileId, Boolean withPermissions) {
        return null;
    }

    @Override
    public List<GetUserDriveInfo> getUsersDrivesInfo() {
        return null;
    }

    @Override
    public void changeDriveMaxSize(Long newAssignedSpace) {

    }
}
