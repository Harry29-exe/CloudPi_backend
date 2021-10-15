package com.cloudpi.cloudpi_backend.files.disk.enpoint;

import com.cloudpi.cloudpi_backend.files.disk.enpoint.requests.CreateNewDrive;
import com.cloudpi.cloudpi_backend.files.disk.enpoint.responses.DiscDetails;
import com.cloudpi.cloudpi_backend.files.disk.enpoint.responses.DiscInfo;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DiscInfoController implements DiscInfoAPI {

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
    public void createNewDrive(CreateNewDrive body) {

    }
}
