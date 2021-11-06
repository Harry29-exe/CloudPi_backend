package com.cloudpi.cloudpi_backend.files.filesystem.enpoints;

import com.cloudpi.cloudpi_backend.files.physical.services.DrivesService;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.CreateFileDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.FileType;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi_backend.files.filesystem.services.FileOnDiscService;
import com.cloudpi.cloudpi_backend.files.filesystem.services.FileRepoService;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
public class FileAPIController implements FileApiDocs {
    private final FileOnDiscService fileOnDiscService;
    private final FileRepoService filesystemService;
    private final DrivesService drivesService;

    public FileAPIController(FileOnDiscService fileOnDiscService,
                             FileRepoService filesystemService,
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
    public void uploadNewFile(FileType fileType, String filepath, MultipartFile file) {
        var createFile = new CreateFileDTO(
                new VirtualPath(filepath),
                file.getSize(),
                fileType
        );

        var createdFile = filesystemService.createAndReturnFile(createFile);
        fileOnDiscService.saveFile(createdFile.getId(), createdFile.getDriveId(), file);
    }

    @Override
    public void forceUploadNewFile(FileType fileType, String filepath, MultipartFile file) {

    }

    @Override
    public Resource downloadFile(String fileId) {
        return fileOnDiscService.readFile(UUID.fromString(fileId));
    }

    @Override
    public void deleteFile(String fileId) {

    }

    @Override
    public void createDirectory(String directoryPath) {

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
