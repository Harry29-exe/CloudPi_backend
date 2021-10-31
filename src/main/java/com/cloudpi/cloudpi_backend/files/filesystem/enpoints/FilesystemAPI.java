package com.cloudpi.cloudpi_backend.files.filesystem.enpoints;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.responses.GetUserDriveInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RequestMapping("/filesystem/")
@Tag(name = "Filesystem API", description = "API for retrieving user's file structure and modify it.")
public interface FilesystemAPI {

    @GetMapping("user/{username}")
    DirectoryDto getUsersFileStructure(@PathVariable("username") String username);

    @PostMapping("directory/{directoryPath}")
    void createDirectory(@PathVariable String directoryPath);

    @GetMapping("user/{username}/")
    DirectoryDto getPartOfUsersFileStructure(
            @PathVariable("username") String username,
            @RequestParam(defaultValue = "2") Integer structureLevels,
            @RequestParam(defaultValue = "/") String fileStructureRoot);

    @GetMapping("info/{fileId}")
    FileDto getFileInfo(
            @PathVariable("fileId") Long fileId,
            @PathVariable(name = "with-permissions", required = false)
                    Boolean withPermissions);

    @GetMapping("users-drives")
    List<GetUserDriveInfo> getUsersDrivesInfo();

    @PostMapping("users-drives/{username}")
    void changeDriveMaxSize(@RequestParam Long newAssignedSpace);

}
