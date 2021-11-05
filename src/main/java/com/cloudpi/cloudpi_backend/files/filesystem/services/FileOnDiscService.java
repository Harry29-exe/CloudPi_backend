package com.cloudpi.cloudpi_backend.files.filesystem.services;

import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface FileOnDiscService {

    @PreAuthorize("@fileAuthorityVerifier.canWrite(Principal, #path)")
    void saveFile(Long fileId, Long driveId, MultipartFile file);

    @PreAuthorize("fileAuthorityVerifier.canRead(Principal, #path)")
    Resource readFile(VirtualPath path);

    @PreAuthorize("fileAuthorityVerifier.canWrite(Principal, #path)")
    void deleteFile(VirtualPath path);

}
