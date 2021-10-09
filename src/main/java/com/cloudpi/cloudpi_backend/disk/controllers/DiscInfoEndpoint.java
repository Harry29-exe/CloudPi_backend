package com.cloudpi.cloudpi_backend.disk.controllers;

import com.cloudpi.cloudpi_backend.disk.responses.DiscDetails;
import com.cloudpi.cloudpi_backend.disk.responses.DiscInfo;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/discs")
public interface DiscInfoEndpoint {

    @Secured(DiscApiAuthorities.GET_DISC_INFO)
    @GetMapping("/{discId}")
    public DiscDetails getDiscInfo(@RequestParam("discId") Long discId);

    @Secured(DiscApiAuthorities.GET_DISC_INFO)
    @GetMapping("")
    List<DiscInfo> getDiscList();

}
