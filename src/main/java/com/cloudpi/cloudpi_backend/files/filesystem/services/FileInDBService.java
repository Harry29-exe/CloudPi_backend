package com.cloudpi.cloudpi_backend.files.filesystem.services;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.CreateFileDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;

import java.util.List;
import java.util.UUID;

public interface FileInDBService {

    UUID createFile(CreateFileDTO fileInfo);

    FileDto createAndReturnFile(CreateFileDTO fileInfo);

    FileDto forceCreateFile(CreateFileDTO fileInfo);

    FileDto getFile(UUID fileId);

    FileDto getFile(VirtualPath path);

    List<FileDto> getFilesByIds(List<UUID> filesIds);

    List<FileDto> getFiles(List<VirtualPath> filesPaths);

    void updateFileSize(UUID fileId, Long newSize);

    void updateFile(FileDto fileDto);

    void deleteFile(UUID fileId);

    void deleteFiles(List<UUID> filesIds);

}
