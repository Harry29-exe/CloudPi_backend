package com.cloudpi.cloudpi_backend.files.filesystem.enpoints;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.FileType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RequestMapping("/files/")
public interface FileAPI {


    @PostMapping(
            path = "image/{imageName}",
            consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    void uploadNewImage(
            @PathVariable String imageName,
            @RequestBody byte[] image,
            Authentication auth);


    @GetMapping(path = "image-preview")
    List<Resource> getImagesPreview(
            @RequestParam(defaultValue = "64") Integer previewResolution,
            @RequestParam(defaultValue = ".jpg") String imageFormat,
            @RequestBody List<String> imageNames);


    @PostMapping(
            path = "file/{filePath}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    void uploadNewFile(
            @PathVariable String filePath,
            @RequestParam(required = false) FileType fileType,
            @RequestBody MultipartFile file);


    @PutMapping(
            path = "file/{filePath}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void forceUploadNewFile(
            @PathVariable String filePath,
            @RequestParam(required = false) FileType fileType,
            @RequestBody MultipartFile file);


    @PostMapping("directory/{directoryPath}")
    void createDirectory(@PathVariable String directoryPath);


    @GetMapping("file/{fileId}")
    Resource downloadFile(@PathVariable Long fileId);


    @GetMapping("directory/{directoryId}")
    Resource compressAndDownloadDirectory(@PathVariable Long directoryId);



    @DeleteMapping("file/{fileId}")
    void deleteFile(@PathVariable Long fileId);


    @DeleteMapping("directory/{directoryId}")
    void deleteDirectory(@PathVariable Long directoryId);


    @DeleteMapping("directory/{directoryId}/force")
    void forceDeleteDirectory(@PathVariable Long directoryId);
}