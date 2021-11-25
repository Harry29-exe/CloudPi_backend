package com.cloudpi.cloudpi_backend.files.filesystem.enpoints;

import com.cloudpi.cloudpi_backend.configuration.springdoc.NotImplemented;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.CreateFileDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.FileType;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi_backend.files.filesystem.services.DirectoryService;
import com.cloudpi.cloudpi_backend.files.filesystem.services.file.FileService;
import com.cloudpi.cloudpi_backend.files.filesystem.services.file.FileInDBService;
import com.cloudpi.cloudpi_backend.files.physical.services.DrivesService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
public class FileAPIController implements FileApiDocs {
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

    @NotImplemented
    @Override
    public void uploadNewImage(String imageName, byte[] image, Authentication auth) {

    }

    @NotImplemented
    @Override
    public List<Resource> getImagesPreview(Integer previewResolution, List<String> imageNames) {
        return null;
    }

    @Override
    public void uploadNewFile(FileType fileType, String filepath, MultipartFile file, Authentication auth) {
        var createFile = new CreateFileDTO(
                new VirtualPath(filepath),
                file.getSize(),
                fileType
        );

        fileService.saveFile(createFile, file);
    }

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

    @Override
    public Resource downloadFile(String fileId) {
        return fileService.readFile(UUID.fromString(fileId));
    }

    @Override
    public void deleteFile(String fileId) {

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
