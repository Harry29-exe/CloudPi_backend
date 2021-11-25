package com.cloudpi.cloudpi_backend.files.filesystem.enpoints.doc;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileStructureDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.responses.GetUserDriveInfo;
import com.cloudpi.cloudpi_backend.files.filesystem.enpoints.FilesystemAPI;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface FilesystemApiDocs extends FilesystemAPI {

    @Override
    FileStructureDTO getFileStructure(
            Integer structureLevels,
            String fileStructureRoot,
            Authentication auth);

    @Override
    FileDto getFileInfo(String fileId, Boolean getWithPermissions);

    @Override
    DirectoryDto getDirInfo(String fileId, Boolean getWithPermissions);

    @Override
    List<GetUserDriveInfo> getUsersVirtualDrivesInfo(List<String> usernames);

    @Override
    void changeVirtualDriveMaxSize(String username, Long newAssignedSpace);

}
