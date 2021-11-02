package com.cloudpi.cloudpi_backend.files.filesystem.services;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;

public interface FileSystemInfoService {

    FileDto getFile(String path);

    DirectoryDto getDirectory(String path);



    void createNewFile(String path, Long size);

    void deleteFile(String path);
}