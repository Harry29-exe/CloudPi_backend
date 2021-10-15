package com.cloudpi.cloudpi_backend.files.permissions.enpoints;

import com.cloudpi.cloudpi_backend.files.permissions.enpoints.responses.GetFilePermissionsResponse;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FilePermissionController implements FilePermissionAPI {
    @Override
    public GetFilePermissionsResponse getFilePermissions() {
        return null;
    }

    @Override
    public List<GetFilePermissionsResponse> getFilesPermissions(List<Integer> fileId) {
        return null;
    }
}
