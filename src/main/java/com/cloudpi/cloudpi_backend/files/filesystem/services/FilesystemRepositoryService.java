package com.cloudpi.cloudpi_backend.files.filesystem.services;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.CreateFileDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;

import java.util.List;

public interface FilesystemRepositoryService {

    void createFile(CreateFileDTO fileInfo, String username);

    FileDto getFile(Long fileId);

    FileDto getFile(VirtualPath path);

    List<FileDto> getFilesByIds(List<Long> filesIds);

    List<FileDto> getFiles(List<VirtualPath> filesPaths);

    void updateFileSize(Long fileId, Long newSize);

    void updateFile(FileDto fileDto);

    void deleteFile(Long fileId);

    void deleteFiles(List<Long> filesIds);


    /**
     * directory related
     */

    void createDirectory(VirtualPath path);

    DirectoryDto getDirectory(VirtualPath path, Integer fileStructureDepth);

    DirectoryDto getDirectory(VirtualPath path);

    void renameDirectory(VirtualPath path, String newName);

    void moveDirectory(VirtualPath path, VirtualPath newPath);

    void deleteDirectory(VirtualPath path);

}
