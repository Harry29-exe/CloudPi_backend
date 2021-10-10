package com.cloudpi.cloudpi_backend.files.permissions.api;

import com.cloudpi.cloudpi_backend.files.permissions.api.responses.GetFilePermissionsResponse;
import com.cloudpi.cloudpi_backend.security.authority.annotations.ContainsPermissions;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/files/")
@ContainsPermissions
public interface FilePermissionAPI {

    //TODO authorization
//    @PreAuthorize("@fileAuthorityVerifier.canRead()")
    @GetMapping("{fileId}")
    GetFilePermissionsResponse getFilePermissions();

    @GetMapping
    List<GetFilePermissionsResponse> getFilesPermissions(@RequestBody List<Integer> fileId);


}
