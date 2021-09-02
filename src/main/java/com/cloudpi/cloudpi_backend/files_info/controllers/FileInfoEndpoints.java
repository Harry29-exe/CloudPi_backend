package com.cloudpi.cloudpi_backend.files_info.controllers;

import com.cloudpi.cloudpi_backend.files_info.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files_info.dto.FileDto;
import org.springframework.security.core.Authentication;

public interface FileInfoEndpoints {

    DirectoryDto getUsersFilesInfo(Authentication userAuth);

    FileDto getFileInfo(Authentication userAuth, String filePath);
}