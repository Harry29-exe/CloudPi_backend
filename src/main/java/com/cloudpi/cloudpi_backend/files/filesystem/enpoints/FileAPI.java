package com.cloudpi.cloudpi_backend.files.filesystem.enpoints;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RequestMapping("/files/")
public interface FileAPI {

    @PostMapping(
            path = "image/{imageName}",
            consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    void uploadNewImage(@PathVariable String imageName, @RequestBody byte[] image, Authentication auth);

    /**
      * @param filePath path to file e.g. bob:/someDirectory/someFile.fileExtension
     * @param file uploaded file
     */
    @Operation(
            summary = "File upload endpoint",
            description = """
                    Allows to upload file to server. Based on filePath server check current user permissions
                    and if user has rights to save file then request body is saved to disc.  
                    """
    )
    @PostMapping(
            path = "file/{filePath}",
            consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    void uploadNewFile(@PathVariable String filePath, @RequestBody byte[] file) throws IOException;

    @PutMapping(
            path = "file/{filePath}",
            consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    void forceUploadNewFile(@PathVariable String filePath, @RequestBody byte[] file) throws IOException;

    @GetMapping("file/{fileId}")
    byte[] downloadFile(@PathVariable String fileId);

    @PostMapping("directory/{directoryPath}")
    void createDirectory(@PathVariable String directoryPath);

    @GetMapping("directory/{directoryId}")
    byte[] compressAndDownloadDirectory(@PathVariable String directoryId);

}