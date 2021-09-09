package com.cloudpi.cloudpi_backend.disc.services;

import com.cloudpi.cloudpi_backend.disc.dto.DiscDTO;

import java.util.List;

public interface DiscService {
    List<DiscDTO> scanDiscs();

    List<DiscDTO> getAllDiscs();

    void modifyDisc(DiscDTO disc);

}
