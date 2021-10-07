package com.cloudpi.cloudpi_backend.disk.services;

import com.cloudpi.cloudpi_backend.disk.dto.DiscDTO;

import java.util.List;

public interface DiscService {
    List<DiscDTO> scanDiscs();

    List<DiscDTO> getAllDiscs();

    void modifyDisc(DiscDTO disc);

}
