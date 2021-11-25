package com.cloudpi.cloudpi_backend.files.filesystem.enpoints;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Directory API", description =
        "API for creating, deleting, modifying and downloading directories")
@RequestMapping("/files/")
public interface DirectoryAPI {

    @PostMapping("directory")
    void createDirectory(
            @RequestParam String directoryPath,
            Authentication auth);

    @GetMapping("directory/{directoryId}")
    Resource compressAndDownloadDirectory(@PathVariable String directoryId);


    @DeleteMapping("directory/{directoryId}")
    void deleteDirectory(@PathVariable String directoryId);


    @DeleteMapping("directory/{directoryId}/force")
    void forceDeleteDirectory(@PathVariable String directoryId);

}
