package com.cloudpi.cloudpi_backend.files.filesystem.enpoints;

import com.cloudpi.cloudpi_backend.files.disk.services.DrivesService;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.CreateFileDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.FileType;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi_backend.files.filesystem.services.FileOnDiscService;
import com.cloudpi.cloudpi_backend.files.filesystem.services.FilesystemRepositoryService;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;

@RestController
public class FileAPIController implements FileAPI {
    private final FileOnDiscService fileOnDiscService;
    private final FilesystemRepositoryService filesystemService;
    private final DrivesService drivesService;

    public FileAPIController(FileOnDiscService fileOnDiscService,
                             FilesystemRepositoryService filesystemService,
                             DrivesService drivesService) {
        this.fileOnDiscService = fileOnDiscService;
        this.filesystemService = filesystemService;
        this.drivesService = drivesService;
    }

    @Override
    public void uploadNewImage(String imageName, byte[] image, Authentication auth) {

    }

    @Override
    public List<Resource> getImagesPreview(Integer previewResolution, String imageFormat, List<String> imageNames) {
        return null;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void uploadNewFile(String filepath, FileType fileType, MultipartFile file) {
        var fileDriveId =  drivesService.getDriveIdAndReserveSpaceOnIt(file.getSize());
        var createFile = new CreateFileDTO(
                fileDriveId,
                new VirtualPath(filepath),
                file.getSize(),
                fileType
        );

        var fileId = filesystemService.createFile(createFile);
        fileOnDiscService.saveFile(fileId, fileDriveId, file);
    }

    @Override
    public void forceUploadNewFile(String filePath, FileType fileType, MultipartFile file) {

    }

    @Override
    public Resource downloadFile(Long fileId) {
        return null;
    }

    @Override
    public void deleteFile(Long fileId) {

    }

    @Override
    public void createDirectory(String directoryPath) {

    }

    @Override
    public Resource compressAndDownloadDirectory(Long directoryId) {
        return null;
    }

    @Override
    public void deleteDirectory(Long directoryId) {

    }

    @Override
    public void forceDeleteDirectory(Long directoryId) {

    }
}
