package com.cloudpi.cloudpi_backend.files.filesystem.services;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileStructureDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;

public interface FilesystemService {

    FileStructureDTO getFileStructure(Integer depth, VirtualPath rootDir);

}
