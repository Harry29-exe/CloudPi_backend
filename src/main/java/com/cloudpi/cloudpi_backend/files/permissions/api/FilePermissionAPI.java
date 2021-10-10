package com.cloudpi.cloudpi_backend.files.permissions.api;

import com.cloudpi.cloudpi_backend.security.authority.annotations.ContainsPermissions;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/files/")
@ContainsPermissions
public interface FilePermissionAPI {

    //TODO authorization
//    @GetMapping("{fileId}")
//    List


}
