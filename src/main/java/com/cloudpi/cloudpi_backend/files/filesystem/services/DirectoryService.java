package com.cloudpi.cloudpi_backend.files.filesystem.services;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.DirectoryEntity;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi_backend.utils.RepoService;

import java.util.Date;
import java.util.UUID;

public interface DirectoryService extends RepoService<DirectoryEntity, UUID> {

    DirectoryDto createDirectory(VirtualPath path);

    DirectoryDto getDirectoryDto(VirtualPath path);

    void updateDirsAfterFileUpdate(VirtualPath modifiedFilePAth, Long fileSizeChange, Date fileModificationDate);

    void renameDirectory(VirtualPath path, String newName);

    void moveDirectory(VirtualPath path, VirtualPath newPath);

    void deleteDirectory(VirtualPath path);

    void forceDeleteDirectory(VirtualPath path);

}
