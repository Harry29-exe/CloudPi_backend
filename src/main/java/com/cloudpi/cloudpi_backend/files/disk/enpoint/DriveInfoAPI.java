package com.cloudpi.cloudpi_backend.files.disk.enpoint;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.atomic.AtomicBoolean;

@RequestMapping("/drives")
public interface DriveInfoAPI {

    @PostMapping("/")
    void createNew(@RequestParam String pathToDrive, @RequestParam Long diskId);

}
