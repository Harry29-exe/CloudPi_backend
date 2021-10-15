package com.cloudpi.cloudpi_backend.files.permissions.enpoints;

import com.cloudpi.cloudpi_backend.files.permissions.enpoints.responses.GetFilePermissionsResponse;
import com.cloudpi.cloudpi_backend.security.authority.annotations.ContainsPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
