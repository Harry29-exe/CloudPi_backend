package com.cloudpi.cloudpi_backend.files.filesystem.enpoints;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import com.cloudpi.cloudpi_backend.files.filesystem.services.FileService;
import com.cloudpi.cloudpi_backend.files.filesystem.services.FileServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/files/")
public class FileAPIController implements FileAPI {
    private FileService fileService;

    public FileAPIController(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public byte[] downloadFile(String fileId) {
        return new byte[0];
    }

    @Override
    public void uploadNewFile(String filepath, byte[] file) throws IOException {
        fileService.saveFile(filepath, new ByteArrayInputStream(file));
    }

    @Override
    public void forceUploadNewFile(String filePath, byte[] file) throws IOException {

    }

    @Override
    public void uploadNewImage(String imageName, byte[] image, Authentication auth) {

    }

    @Override
    public void createDirectory(String directoryPath) {

    }

    @Override
    public byte[] compressAndDownloadDirectory(String directoryId) {
        return new byte[0];
    }
}
