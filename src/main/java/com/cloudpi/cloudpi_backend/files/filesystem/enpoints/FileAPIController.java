package com.cloudpi.cloudpi_backend.files.filesystem.enpoints;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import com.cloudpi.cloudpi_backend.files.filesystem.services.FileService;
import com.cloudpi.cloudpi_backend.files.filesystem.services.FileServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/files/")
public class FileAPIController implements FileAPI {
    private FileServiceImpl fileService;

    public FileAPIController(FileServiceImpl fileService) {
        this.fileService = fileService;
    }

    @Override
    public DirectoryDto getUsersFileStructure(String username) {
        return null;
    }

    @Override
    public FileDto getFileInfo(String filePath) {
        return null;
    }

    @PostMapping(
            path = "upload-file/{filepath}",
            consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public void uploadNewFile(@PathVariable String filepath, @RequestBody byte[] file) throws IOException {
        fileService.saveFile(filepath, file);
    }
}
