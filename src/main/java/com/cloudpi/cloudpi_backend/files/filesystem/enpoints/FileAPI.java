package com.cloudpi.cloudpi_backend.files.filesystem.enpoints;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RequestMapping("/files/")
public interface FileAPI {

    @Operation(
            summary = "Image uploads",
            description = """
                    <h1>Not implemented</h1>
                    Allows to upload image to server. Endpoint behave similar to POST file/{filePath}
                    but this method use user .images folder instead of precisely defined path.
                    """,
            parameters = @Parameter(name = "imageName", description = "Name with extension of image to upload", example = "funny-cat.jpg"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "success"),
                    @ApiResponse(responseCode = "400", description = "when request does not have body or image name does not contain file extension"),
                    @ApiResponse(responseCode = "401", description = "when user is not logged"),
                    @ApiResponse(responseCode = "403", description = "when user does not have permission to save image e.g. not enough space"),
                    @ApiResponse(responseCode = "409", description = "when image with that name already exist")
            }
    )
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

    @Operation(
            summary = "Uploads file",
            description = """
                    Allows to upload file to server. Based on filePath server check current user permissions
                    and if user has rights to save file then request body is saved to disc.
                    """
    )
    @PostMapping(
            path = "file/{filePath}",
            consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    void uploadNewFile(
            @Parameter(example = "steve:/dir1/dir2/my-awesome-file.awesome")
            @PathVariable String filePath,
            @RequestBody byte[] file);



    @Operation(
            summary = "Forces file upload",
            description = """
                    Allows to upload file to server. Based on filePath server check current user permissions
                    and if user has rights to save file then request body is saved to disc. Unlike Post method
                    PUT overrides existing files.
                    """)
    @PutMapping(
            path = "file/{filePath}",
            consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    void forceUploadNewFile(@PathVariable String filePath, @RequestBody byte[] file);



    @Operation(
            summary = "Downloads file",
            description = """
                    Downloads file with given id.
                    """)
    @GetMapping("file/{fileId}")
    byte[] downloadFile(@PathVariable Long fileId);



    @Operation(
            summary = "Compresses directory and downloads it",
            description = """
                    Compress directory to .7z and sends it in response body.
                    """)
    @GetMapping("directory/{directoryId}")
    byte[] compressAndDownloadDirectory(@PathVariable Long directoryId);

}