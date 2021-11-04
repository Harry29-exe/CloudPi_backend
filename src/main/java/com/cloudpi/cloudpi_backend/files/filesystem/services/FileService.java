package com.cloudpi.cloudpi_backend.files.filesystem.services;

import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    @PreAuthorize("@fileAuthorityVerifier.canWrite(Principal, #path)")
    void saveFile(VirtualPath path, InputStream file) throws IOException;

    @PreAuthorize("fileAuthorityVerifier.canRead(Principal, #path)")
    Resource readFile(VirtualPath path);

    @PreAuthorize("fileAuthorityVerifier.canWrite(Principal, #path)")
    void deleteFile(VirtualPath path);

}
