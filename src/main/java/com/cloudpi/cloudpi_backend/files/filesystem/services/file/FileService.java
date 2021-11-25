package com.cloudpi.cloudpi_backend.files.filesystem.services.file;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.CreateFileDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileService {

    @PreAuthorize("@fileAuthorityVerifier.canWrite(Principal, #path)")
    void saveFile(CreateFileDTO fileInfo, MultipartFile file);

    @PreAuthorize("fileAuthorityVerifier.canRead(Principal, #path)")
    Resource readFile(VirtualPath path);

    @PreAuthorize("fileAuthorityVerifier.canRead(Principal, #path)")
    Resource readFile(UUID fileId);

    @PreAuthorize("fileAuthorityVerifier.canWrite(Principal, #path)")
    void deleteFile(VirtualPath path);

}
