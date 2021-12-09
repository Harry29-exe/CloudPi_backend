package com.cloudpi.cloudpi_backend.files.filesystem.enpoints.doc;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileStructureDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.responses.GetUserDriveInfo;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface FilesystemApiDocs {


    FileStructureDTO getFileStructure(
            Integer structureLevels,
            String fileStructureRoot,
            Authentication auth);


    FileDto getFileInfo(String fileId, Boolean getWithPermissions);


    DirectoryDto getDirInfo(String fileId, Boolean getWithPermissions);


    List<GetUserDriveInfo> getUsersVirtualDrivesInfo(List<String> usernames);

    void changeVirtualDriveMaxSize(String username, Long newAssignedSpace);

}
