package com.cloudpi.cloudpi_backend.files.filesystem.enpoints;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
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

    @GetMapping("user/{username}")
    DirectoryDto getUsersFileStructure(@PathVariable("username") String username);

//    @GetMapping("user/{username}/s")

    @GetMapping("info/{filename}")
    FileDto getFileInfo(@PathVariable("filename") String filePath);

//    @PostMapping(
//            name = "upload-file/{filepath}",
//            consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE
//    )
//    void uploadNewFile(@PathVariable String filepath, @RequestParam("file") MultipartFile file) throws IOException;

    @PostMapping(
            name = "upload-file/{filepath}",
            consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    void uploadNewFile(@PathVariable String filepath, @RequestBody byte[] file) throws IOException;
}