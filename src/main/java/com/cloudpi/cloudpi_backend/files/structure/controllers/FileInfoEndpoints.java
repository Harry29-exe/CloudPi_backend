package com.cloudpi.cloudpi_backend.files.structure.controllers;

import com.cloudpi.cloudpi_backend.files.structure.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.structure.dto.FileDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/files/info")
public interface FileInfoEndpoints {

    @PreAuthorize("#principal")
    DirectoryDto getUsersFilesInfo(Authentication userAuth);

    FileDto getFileInfo(Authentication userAuth, String filePath);
}