package com.cloudpi.cloudpi_backend.files.disk.enpoint;

import com.cloudpi.cloudpi_backend.files.disk.dto.responses.GetDriveResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@RequestMapping("/drives")
public interface DriveInfoAPI {

    @PostMapping
    void createNew(@RequestParam String pathToDrive, @RequestParam Long diskId);

    @GetMapping
    List<GetDriveResponse> getDrivesList();

    @DeleteMapping
    void delete(@RequestParam Long driveId);
}
