package com.cloudpi.cloudpi_backend.files.filesystem.services.file;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.CreateFileDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi_backend.files.physical.services.DrivesService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    private final DrivesService drivesService;
    private final FileInDBService dbFileService;

    public FileServiceImpl(DrivesService drivesService, FileInDBService dbFileService) {
        this.drivesService = drivesService;
        this.dbFileService = dbFileService;
    }

    @Override
    public void saveFile(CreateFileDTO fileInfo, MultipartFile file) {
        var createdFile = dbFileService.createFile(fileInfo);
        var physicalPath = drivesService.getFilePath(createdFile.getId());

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
//        var fileEntity = fileRepository.findById(fileId)
//                //TODO
//                .orElseThrow(IllegalStateException::new);
//        var drivePhysicalPath = fileEntity.getDrive().getPathToDrive();
//        return new FileSystemResource(drivePhysicalPath + "/" + fileEntity.getId());
        throw new NotImplementedException();
    }

    @Override
    public Resource readFile(VirtualPath path) {
        return null;
    }

    @Override
    public void deleteFile(VirtualPath path) {

    }
}
