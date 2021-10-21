package com.cloudpi.cloudpi_backend.files.permissions.enpoints;

import com.cloudpi.cloudpi_backend.files.permissions.enpoints.responses.GetFilePermissionsResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/files/")
public interface FilePermissionAPI {

    //TODO authorization
//    @PreAuthorize("@fileAuthorityVerifier.canRead()")
    @GetMapping("{fileId}")
    GetFilePermissionsResponse getFilePermissions();

    @GetMapping
    List<GetFilePermissionsResponse> getFilesPermissions(@RequestBody List<Integer> fileId);


}
