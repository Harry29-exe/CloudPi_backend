package com.cloudpi.cloudpi_backend.files.filesystem.enpoints;

import com.cloudpi.cloudpi_backend.files.filesystem.pojo.FileType;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            path = "file",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    void uploadNewFile(
            @RequestParam(required = false) FileType fileType,
            @RequestParam String filepath,
            @RequestParam MultipartFile file);


    @PutMapping(
            path = "file",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void forceUploadNewFile(
            @RequestParam(required = false) FileType fileType,
            @RequestParam String filepath,
            @RequestBody MultipartFile file);


    @PostMapping("directory/{directoryPath}")
    void createDirectory(@PathVariable String directoryPath);


    @GetMapping("file/{fileId}")
    Resource downloadFile(@PathVariable String fileId);


    @GetMapping("directory/{directoryId}")
    Resource compressAndDownloadDirectory(@PathVariable String directoryId);


    @DeleteMapping("file/{fileId}")
    void deleteFile(@PathVariable String fileId);


    @DeleteMapping("directory/{directoryId}")
    void deleteDirectory(@PathVariable String directoryId);


    @DeleteMapping("directory/{directoryId}/force")
    void forceDeleteDirectory(@PathVariable String directoryId);
}