package com.cloudpi.cloudpi_backend.files.filesystem.enpoints;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileStructureDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.responses.GetUserDriveInfo;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi_backend.files.filesystem.services.FilesystemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FilesystemAPIController implements FilesystemAPI {
    private final FilesystemInfoService fsInfoService;

    public FilesystemAPIController(FilesystemInfoService fsInfoService) {
        this.fsInfoService = fsInfoService;
    }

    @Override
    public FileStructureDTO getPartOfUsersFileStructure(
            Integer structureLevels,
            String fileStructureRoot,
            Authentication auth) {

        return fsInfoService.getFileStructure(
                structureLevels,
                fileStructureRoot.equals("/")?
                        new VirtualPath(auth.getName()):
                        new VirtualPath(fileStructureRoot)
        );
    }

    @Override
    public FileDto getFileInfo(String fileId, Boolean getWithPermissions) {
        return null;
    }

    @Override
    public DirectoryDto getDirInfo(String fileId, Boolean getWithPermissions) {
        return null;
    }

    @Override
    public List<GetUserDriveInfo> getUsersDrivesInfo(List<String> usernames) {
        return null;
    }

    @Override
    public void changeDriveMaxSize(String username, Long newAssignedSpace) {

    }
}
