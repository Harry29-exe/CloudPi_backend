package com.cloudpi.cloudpi_backend.files.filesystem.enpoints;

import com.cloudpi.cloudpi_backend.configuration.springdoc.NotImplemented;
import com.cloudpi.cloudpi_backend.configuration.springdoc.Stability;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.CreateFileDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.FileType;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi_backend.files.filesystem.services.DirectoryService;
import com.cloudpi.cloudpi_backend.files.filesystem.services.file.FileInDBService;
import com.cloudpi.cloudpi_backend.files.filesystem.services.file.FileService;
import com.cloudpi.cloudpi_backend.files.physical.services.DrivesService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

@RestController
public class FileAPIController implements FileAPI {
    private final FileService fileService;
    private final FileInDBService fileInDBService;
    private final DrivesService drivesService;
    private final DirectoryService dirService;

    public FileAPIController(FileService fileService,
                             FileInDBService fileInDBService,
                             DrivesService drivesService,
                             DirectoryService dirService) {
        this.fileService = fileService;
        this.fileInDBService = fileInDBService;
        this.drivesService = drivesService;
        this.dirService = dirService;
    }

    @Override
    @Stability.InitialTests
    public void uploadNewFile(FileType fileType, String filepath, MultipartFile file, Authentication auth) {
        var createFile = new CreateFileDTO(
                new VirtualPath(filepath),
                file.getSize(),
                fileType
        );

        fileService.saveFile(createFile, file);
    }


    @NotImplemented.LOW
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void forceUploadNewFile(FileType fileType, String filepath, MultipartFile file, Authentication auth) {
        throw new NotImplementedException();
//        var createFile = new CreateFileDTO(
//                new VirtualPath(filepath),
//                file.getSize(),
//                fileType
//        );
//
//
//        var createdFile = fileInDBService.createFile(createFile);
//        fileService.saveFile(createdFile.getId(), file);
    }


    @NotImplemented.LOW
    @Override
    public void uploadNewImage(String imageName, byte[] image, Authentication auth) {
        throw new NotImplementedException();
    }


    @Stability.InitialTests
    @Override
    public Resource downloadFile(String fileId) {
        return fileService.readFile(UUID.fromString(fileId));
    }


    @NotImplemented.LOW
    @Override
    public List<Resource> getImagesPreview(Integer previewResolution, List<String> imageNames) {
        return null;
    }

    @NotImplemented.LOW
    @Override
    public Resource compressAndDownloadDirectory(String directoryId) {
        return null;
    }

    @NotImplemented.LOW
    @Override
    public void forceDeleteDirectory(String directoryId) {

    }

    @NotImplemented.HIGH
    @Override
    public void deleteFile(UUID fileId) {
        fileService.deleteFile(fileId);
    }

    @NotImplemented.MEDIUM
    @Override
    public void deleteFiles(@NotEmpty List<UUID> fileId) {

    }

}
