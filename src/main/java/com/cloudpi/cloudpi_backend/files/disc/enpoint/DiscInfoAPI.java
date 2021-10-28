package com.cloudpi.cloudpi_backend.files.disc.enpoint;

import com.cloudpi.cloudpi_backend.files.disc.dto.responses.DiscDetails;
import com.cloudpi.cloudpi_backend.files.disc.dto.responses.DiscInfo;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/discs")
public interface DiscInfoAPI {

    @Secured(DiscApiAuthorities.GET_DISC_INFO)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{discId}")
    DiscDetails getDiscInfo(@RequestParam("discId") Long discId);

    @Secured(DiscApiAuthorities.GET_DISC_INFO)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<DiscInfo> getDiscList();

    @Secured(DiscApiAuthorities.GET_DISC_INFO)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/drive/{driveId}")
    List<DiscInfo> getDrivesDisc(@RequestParam("driveId") String driveId);

    @Secured(DiscApiAuthorities.MODIFY_DISCS)
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{discId}/unmount")
    void unmountDrive(@RequestParam(name = "discId") Long discId);

    @Secured(DiscApiAuthorities.MODIFY_DISCS)
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{discId}/mount")
    void mountDrive(@RequestParam(name = "discId") Long discId);


}
