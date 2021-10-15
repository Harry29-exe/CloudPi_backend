package com.cloudpi.cloudpi_backend.files.filesystem.enpoints;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/files/info")
public interface FileInfoAPI {

//    @PreAuthorize("#principal = ")
    DirectoryDto getUsersFilesInfo(Authentication userAuth);

    FileDto getFileInfo(Authentication userAuth, String filePath);
}