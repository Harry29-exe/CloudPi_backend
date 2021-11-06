package com.cloudpi.cloudpi_backend.files.physical.enpoint;

import com.cloudpi.cloudpi_backend.files.physical.dto.responses.GetDriveResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/drives")
public interface DiscStorageInfoAPI {

    @PostMapping
    void createNew(@RequestParam String pathToDrive, @RequestParam Long diskId);

    @GetMapping
    List<GetDriveResponse> getDrivesList();

    @DeleteMapping
    void delete(@RequestParam Long driveId);
}
