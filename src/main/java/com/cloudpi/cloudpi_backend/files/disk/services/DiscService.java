package com.cloudpi.cloudpi_backend.files.disk.services;

import com.cloudpi.cloudpi_backend.files.disk.dto.DiscDTO;

import java.util.List;

public interface DiscService {
    List<DiscDTO> scanDiscs();

    List<DiscDTO> getAllDiscs();

    void modifyDisc(DiscDTO disc);

}
