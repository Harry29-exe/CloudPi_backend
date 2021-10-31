package com.cloudpi.cloudpi_backend.files.filesystem.services;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.VirtualPathDTO;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    @PreAuthorize("@fileAuthorityVerifier.canWrite(Principal, #path)")
    void saveFile(VirtualPathDTO path, InputStream file) throws IOException;

    @PreAuthorize("@fileAuthorityVerifier.canWrite(Principal, #path)")
    void createDirectory(VirtualPathDTO path);

    @PreAuthorize("fileAuthorityVerifier.canRead(Principal, #path)")
    Resource readFile(VirtualPathDTO path);

    @PreAuthorize("fileAuthorityVerifier.canWrite(Principal, #path)")
    void deleteFile(VirtualPathDTO path);

}
