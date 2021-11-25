package com.cloudpi.cloudpi_backend.files.filesystem.enpoints;

import com.cloudpi.cloudpi_backend.files.filesystem.pojo.FileType;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RequestMapping("/files/")
@Tag(name = "File API",
        description = "API for uploading, downloading and deleting files")
public interface FileAPI {

    @PostMapping(
            path = "file",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    void uploadNewFile(
            @RequestParam(defaultValue = "UNDEFINED") FileType fileType,
            @RequestParam String filepath,
            @RequestParam MultipartFile file,
            Authentication auth);


    @PutMapping(
            path = "file",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void forceUploadNewFile(
            @RequestParam(defaultValue = "UNDEFINED") FileType fileType,
            @RequestParam String filepath,
            @RequestBody MultipartFile file,
            Authentication auth);


    @PostMapping(
            path = "image/{imageName}",
            consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    void uploadNewImage(
            @PathVariable String imageName,
            @RequestBody byte[] image,
            Authentication auth);



    @GetMapping("file/{fileId}")
    Resource downloadFile(@PathVariable String fileId);


    @GetMapping("directory/{directoryId}")
    Resource compressAndDownloadDirectory(@PathVariable String directoryId);


    @GetMapping(path = "image-preview")
    List<Resource> getImagesPreview(
            @RequestParam(defaultValue = "64") Integer previewResolution,
            @RequestBody List<String> imageNames);



    @DeleteMapping("directory/{directoryId}/force")
    void forceDeleteDirectory(@PathVariable String directoryId);


}