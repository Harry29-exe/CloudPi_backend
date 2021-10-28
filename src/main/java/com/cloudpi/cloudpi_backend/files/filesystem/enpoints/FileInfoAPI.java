package com.cloudpi.cloudpi_backend.files.filesystem.enpoints;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;

@RequestMapping("/file-info/")
public interface FileInfoAPI {

    @GetMapping("user/{username}")
    DirectoryDto getUsersFileStructure(@PathVariable("username") String username);

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

}
