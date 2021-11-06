package com.cloudpi.cloudpi_backend.files.filesystem.services;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;

public interface DirectoryRepoService {

    void createDirectory(VirtualPath path);

    DirectoryDto getDirectory(VirtualPath path, Integer fileStructureDepth);

    DirectoryDto getDirectory(VirtualPath path);

    void renameDirectory(VirtualPath path, String newName);

    void moveDirectory(VirtualPath path, VirtualPath newPath);

    void deleteDirectory(VirtualPath path);

}
