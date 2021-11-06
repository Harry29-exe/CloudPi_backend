package com.cloudpi.cloudpi_backend.files.physical.services;

import com.cloudpi.cloudpi_backend.files.physical.dto.DiscDTO;

import java.util.List;

public interface DiscService {
    List<DiscDTO> scanDiscs();

    List<DiscDTO> getAllDiscs();

    void modifyDisc(DiscDTO disc);

}
