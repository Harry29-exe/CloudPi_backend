package com.cloudpi.cloudpi_backend.files.filesystem.enpoints;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileStructureDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.responses.GetUserDriveInfo;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface FilesystemApiDocs extends FilesystemAPI {

    @Override
    FileStructureDTO getPartOfUsersFileStructure(
            Integer structureLevels,
            String fileStructureRoot,
            Authentication auth);

    @Override
    FileDto getFileInfo(String fileId, Boolean getWithPermissions);

    @Override
    DirectoryDto getDirInfo(String fileId, Boolean getWithPermissions);

    @Override
    List<GetUserDriveInfo> getUsersDrivesInfo(List<String> usernames);

    @Override
    void changeDriveMaxSize(String username, Long newAssignedSpace);

}
