package com.cloudpi.cloudpi_backend.files.filesystem.services;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileStructureDTO;

public interface FileSystemInfoService {

    FileDto getFile(String path);

    DirectoryDto getDirectory(String path);

    FileStructureDTO getFileStructure(String pathToDirectory, Integer structureDepth);

    void createNewFile(String path, Long size);

    void deleteFile(String path);
}