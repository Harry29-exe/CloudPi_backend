package com.cloudpi.cloudpi_backend.files.filesystem.enpoints.doc;

import com.cloudpi.cloudpi_backend.files.filesystem.enpoints.DirectoryAPI;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi_backend.files.filesystem.services.DirectoryService;
import com.cloudpi.cloudpi_backend.files.filesystem.services.file.FileInDBService;
import com.cloudpi.cloudpi_backend.files.filesystem.services.file.FileService;
import com.cloudpi.cloudpi_backend.files.physical.services.DrivesService;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DirectoryAPIController implements DirectoryAPI {
    private final FileService fileService;
    private final FileInDBService fileInDBService;
    private final DrivesService drivesService;
    private final DirectoryService dirService;


    public DirectoryAPIController(FileService fileService, FileInDBService fileInDBService, DrivesService drivesService, DirectoryService dirService) {
        this.fileService = fileService;
        this.fileInDBService = fileInDBService;
        this.drivesService = drivesService;
        this.dirService = dirService;
    }

    @Override
    public void createDirectory(String directoryPath, Authentication auth) {
        dirService.createDirectory(new VirtualPath(directoryPath));
    }

    @Override
    public Resource compressAndDownloadDirectory(String directoryId) {
        return null;
    }

    @Override
    public void deleteDirectory(String directoryId) {

    }

    @Override
    public void forceDeleteDirectory(String directoryId) {

    }

}
