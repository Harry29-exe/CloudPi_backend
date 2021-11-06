package com.cloudpi.cloudpi_backend.files.physical.enpoint;

import com.cloudpi.cloudpi_backend.files.physical.dto.responses.DiscDetails;
import com.cloudpi.cloudpi_backend.files.physical.dto.responses.DiscInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/discs")
public interface DiskInfoAPI {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{discId}")
    DiscDetails getDiscInfo(@RequestParam("discId") Long discId);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<DiscInfo> getDiscList();

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/drive/{driveId}")
    List<DiscInfo> getDrivesDisc(@RequestParam("driveId") String driveId);

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{discId}/unmount")
    void unmountDrive(@RequestParam(name = "discId") Long discId);

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{discId}/mount")
    void mountDrive(@RequestParam(name = "discId") Long discId);


}
