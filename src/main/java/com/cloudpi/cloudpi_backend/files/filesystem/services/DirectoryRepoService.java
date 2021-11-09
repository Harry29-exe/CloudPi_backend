package com.cloudpi.cloudpi_backend.files.filesystem.services;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;

import java.util.Date;

public interface DirectoryRepoService {

    void createDirectory(VirtualPath path);

    DirectoryDto getDirectoryDto(VirtualPath path);

    void updateDirsAfterFileUpdate(VirtualPath modifiedFilePAth, Long fileSizeChange, Date fileModificationDate);

    void renameDirectory(VirtualPath path, String newName);

    void moveDirectory(VirtualPath path, VirtualPath newPath);

    void deleteDirectory(VirtualPath path);

}
