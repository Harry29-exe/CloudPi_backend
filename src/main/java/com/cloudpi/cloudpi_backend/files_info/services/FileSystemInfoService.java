package com.cloudpi.cloudpi_backend.files_info.services;

import com.cloudpi.cloudpi_backend.files_info.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files_info.dto.FileDto;

public interface FileSystemInfoService {

    FileDto getFile(String path);

    DirectoryDto getDirectory(String path);

    void createNewFile(String path, Long size);

    void deleteFile(String path);
}