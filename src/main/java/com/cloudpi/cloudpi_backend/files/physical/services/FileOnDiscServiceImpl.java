package com.cloudpi.cloudpi_backend.files.physical.services;

import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.FileRepository;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FileOnDiscServiceImpl implements FileOnDiscService {
    private final DrivesService drivesService;
    private final FileRepository fileRepository;

    public FileOnDiscServiceImpl(DrivesService drivesService,
                                 FileRepository fileRepository) {
        this.drivesService = drivesService;
        this.fileRepository = fileRepository;
    }

    @Override
    public void saveFile(UUID fileId, MultipartFile file) {
        var physicalPath = drivesService.fileIdToPath(fileId);

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
    public Resource readFile(UUID fileId) {
        var fileEntity = fileRepository.findById(fileId)
                //TODO
                .orElseThrow(IllegalStateException::new);
        var drivePhysicalPath = fileEntity.getDrive().getPathToDrive();
        return new FileSystemResource(drivePhysicalPath + "/" + fileEntity.getId());
    }

    @Override
    public Resource readFile(VirtualPath path) {
        return null;
    }

    @Override
    public void deleteFile(VirtualPath path) {

    }
}
