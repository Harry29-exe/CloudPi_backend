package com.cloudpi.cloudpi_backend.files.physical.enpoint;

import com.cloudpi.cloudpi_backend.files.physical.dto.responses.DiscDetails;
import com.cloudpi.cloudpi_backend.files.physical.dto.responses.DiscInfo;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DiskInfoController implements DiskInfoAPI {

    @Override
    public DiscDetails getDiscInfo(Long discId) {
        return null;
    }

    @Override
    public List<DiscInfo> getDiscList() {
        return null;
    }

    @Override
    public List<DiscInfo> getDrivesDisc(String driveId) {
        return null;
    }

    @Override
    public void unmountDrive(Long discId) {

    }

    @Override
    public void mountDrive(Long discId) {

    }
}
