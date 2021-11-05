package com.cloudpi.cloudpi_backend.files.filesystem.services;

import com.cloudpi.cloudpi_backend.files.disk.services.DrivesService;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

@Service
public class FileOnDiscServiceImpl implements FileOnDiscService {
    private final DrivesService drivesService;

    public FileOnDiscServiceImpl(DrivesService drivesService) {
        this.drivesService = drivesService;
    }

    @Override
    public void saveFile(Long fileId, Long driveId, MultipartFile file) {
        var physicalPath = drivesService.fileIdToPath(fileId, driveId);

        //TODO to potrzebuje testu (teoretycznie powinno działać:
        //          spóbuj przekazać ownership pliku
        //          jesli wyrzuci wyjątek (różne fizyczne dyski) spróbuj przekopiować plik
        try {
            file.transferTo(physicalPath.toFile());
        } catch (IOException e) {
            try {
                file.transferTo(physicalPath);
            } catch (IOException ex) {
                //TODO change exception
                throw new IllegalStateException();
            }
        }
    }

    @Override
    public Resource readFile(VirtualPath path) {
        return null;
    }

    @Override
    public void deleteFile(VirtualPath path) {

    }
}
