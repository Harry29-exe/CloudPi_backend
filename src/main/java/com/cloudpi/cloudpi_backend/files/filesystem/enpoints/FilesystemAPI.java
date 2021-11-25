package com.cloudpi.cloudpi_backend.files.filesystem.enpoints;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileStructureDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.responses.GetUserDriveInfo;
import com.cloudpi.cloudpi_backend.configuration.springdoc.SpringDocUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/filesystem/")
@Tag(name = "Filesystem API",
        description = SpringDocUtils.NOT_IMPLEMENTED +
                "API for retrieving user's file structure and file info and modify it.")
public interface FilesystemAPI {

    @GetMapping("file-structure")
    FileStructureDTO getFileStructure(
            @RequestParam(defaultValue = "0") Integer structureLevels,
            @RequestParam(defaultValue = "/") String fileStructureRoot,
            Authentication auth);

    @GetMapping("file/{fileId}")
    FileDto getFileInfo(
            @PathVariable("fileId") String fileId,
            @PathVariable(name = "with-permissions", required = false)
                    Boolean getWithPermissions);

    @GetMapping("dir/{dirId}")
    DirectoryDto getDirInfo(
            @PathVariable("fileId") String fileId,
            @PathVariable(name = "with-permissions", required = false)
                    Boolean getWithPermissions);

    @GetMapping("user-drive")
    List<GetUserDriveInfo> getUsersDrivesInfo(
            @PathVariable List<String> usernames);

    @PostMapping("user-drive")
    void changeDriveMaxSize(
            @PathVariable String username,
            @RequestParam Long newAssignedSpace
    );

}
