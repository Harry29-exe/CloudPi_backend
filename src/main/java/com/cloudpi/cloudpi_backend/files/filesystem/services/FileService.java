package com.cloudpi.cloudpi_backend.files.filesystem.services;

import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    @PreAuthorize("@fileAuthorityVerifier.canWrite(Principal, #path)")
    void saveFile(String path, InputStream file) throws IOException;

    @PreAuthorize("@fileAuthorityVerifier.canWrite(Principal, #path)")
    void createDirectory(String path);

    Resource readFile(String path);

    void deleteFile(String path);

}
