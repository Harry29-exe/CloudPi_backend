package com.cloudpi.cloudpi_backend.files.disc.services;

import com.cloudpi.cloudpi_backend.files.disc.dto.DiscDTO;

import java.util.List;

public interface DiscService {
    List<DiscDTO> scanDiscs();

    List<DiscDTO> getAllDiscs();

    void modifyDisc(DiscDTO disc);

}
