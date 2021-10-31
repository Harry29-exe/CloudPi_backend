package com.cloudpi.cloudpi_backend.files.filesystem.enpoints;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.VirtualPathDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.services.FileService;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
public class FileAPIController implements FileApiDocs {
    private final FileService fileService;

    public FileAPIController(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public byte[] downloadFile(Long fileId) {
        return new byte[0];
    }

    @Override
    public void uploadNewFile(String filepath, byte[] file) {
        try {
            fileService.saveFile(new VirtualPathDTO(filepath), new ByteArrayInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createDirectory(String directoryPath) {

    }

    @Override
    public void forceUploadNewFile(String filePath, byte[] file) {

    }

    @Override
    public void uploadNewImage(String imageName, byte[] image, Authentication auth) {

    }

    @Override
    public List<Resource> getImagesPreview(Integer previewResolution, String imageFormat, List<String> imageNames) {
        return null;
    }

    @Override
    public byte[] compressAndDownloadDirectory(Long directoryId) {
        return new byte[0];
    }

    @Override
    public void deleteFile(Long fileId) {

    }

    @Override
    public void deleteDirectory(Long directoryId) {

    }

    @Override
    public void forceDeleteDirectory(Long directoryId) {

    }
}
