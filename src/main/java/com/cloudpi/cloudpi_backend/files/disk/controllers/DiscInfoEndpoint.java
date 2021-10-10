package com.cloudpi.cloudpi_backend.files.disk.controllers;

import com.cloudpi.cloudpi_backend.files.disk.requests.CreateNewDrive;
import com.cloudpi.cloudpi_backend.files.disk.responses.DiscDetails;
import com.cloudpi.cloudpi_backend.files.disk.responses.DiscInfo;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/disc")
public interface DiscInfoEndpoint {

    @Secured(DiscApiAuthorities.GET_DISC_INFO)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{discId}")
    public DiscDetails getDiscInfo(@RequestParam("discId") Long discId);

    @Secured(DiscApiAuthorities.GET_DISC_INFO)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<DiscInfo> getDiscList();

    @Secured(DiscApiAuthorities.GET_DISC_INFO)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/drive/{driveId}")
    List<DiscInfo> getDrivesDisc(@RequestParam("driveId") String driveId);

    @Secured(DiscApiAuthorities.MODIFY_DISCS)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{discId}")
    void createNewDrive(@RequestBody @Valid CreateNewDrive body);

}
