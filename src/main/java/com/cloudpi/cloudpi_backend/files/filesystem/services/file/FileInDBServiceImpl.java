package com.cloudpi.cloudpi_backend.files.filesystem.services.file;

import com.cloudpi.cloudpi_backend.exepctions.files.FileNotFoundException;
import com.cloudpi.cloudpi_backend.exepctions.files.PathNotFoundException;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.CreateFileDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.mappers.FileMapper;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.FileEntity;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.FileType;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.DirectoryRepository;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.FileRepository;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.VirtualDriveRepository;
import com.cloudpi.cloudpi_backend.files.filesystem.services.DirectoryService;
import com.cloudpi.cloudpi_backend.files.physical.repositories.DriveRepository;
import com.cloudpi.cloudpi_backend.files.physical.services.DrivesService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FileInDBServiceImpl implements FileInDBService {
    private final FileRepository fileRepo;
    private final DirectoryRepository dirRepo;

    private final DirectoryService dirService;

    private final DrivesService drivesService;
    private final DriveRepository driveRepository;
    private final VirtualDriveRepository virtualDriveRepository;

    public FileInDBServiceImpl(FileRepository fileRepo,
                               DirectoryRepository dirRepo,
                               DirectoryService dirService, DrivesService drivesService,
                               DriveRepository driveRepository,
                               VirtualDriveRepository virtualDriveRepository) {
        this.fileRepo = fileRepo;
        this.dirRepo = dirRepo;
        this.dirService = dirService;
        this.drivesService = drivesService;
        this.driveRepository = driveRepository;
        this.virtualDriveRepository = virtualDriveRepository;
    }

//    @Override
//    @Transactional(isolation = Isolation.REPEATABLE_READ)
//    public UUID createFile(CreateFileDTO fileInfo) {
//        var createdFile = this.createFileEntity(fileInfo);
//
//        return createdFile.getId();
//    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public FileDto createFile(CreateFileDTO fileInfo) {
        var createdFile = this.createFileEntity(fileInfo);

        return FileMapper.INSTANCE.fileEntityToDTO(createdFile);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public FileDto forceCreateFile(CreateFileDTO fileInfo) {
        var path = fileInfo.path().getPath();
        if(fileRepo.existsByPath(path)) {
            fileRepo.deleteByPath(path);
        }

        return this.createFile(fileInfo);
    }

    @Override
    public FileDto getFile(UUID fileId) {
        return fileRepo.findDtoById(fileId)
                .orElseThrow(FileNotFoundException::new);
    }

    @Override
    public FileDto getFile(VirtualPath path) {
        return fileRepo.findDtoByPath(path.getPath())
                .orElseThrow(FileNotFoundException::new);
    }

    @Override
    public List<FileDto> getFilesByIds(List<UUID> filesIds) {
        return fileRepo.findAllDtoByIdIn(filesIds);
    }

    @Override
    public List<FileDto> getFiles(List<VirtualPath> filesPaths) {
        return fileRepo.findAllDtoByPathIn(filesPaths.stream()
                .map(VirtualPath::getPath).toList()
        );
    }

    @Override
    @Transactional
    public void updateFileSize(UUID fileId, Long newSize) {
        var modificationDate = new Date();
        var file = fileRepo.findDtoById(fileId)
                .orElseThrow(PathNotFoundException::noSuchFile);
        var fileSizeChange = file.getSize() - newSize;

        fileRepo.updateFileSize(fileId, newSize, modificationDate);
        dirService.updateDirsAfterFileUpdate(
                new VirtualPath(file.getPath()),
                fileSizeChange,
                modificationDate);
    }

    //TODO
    @Override
    public void updateFile(FileDto fileDto) {
        throw new NotImplementedException();
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteFile(UUID fileId) {
        var fileToDelete = fileRepo.findById(fileId)
                .orElseThrow(FileNotFoundException::new);

        drivesService.updateDriveUsedSpace(
                fileToDelete.getDrive().getId(),
                -fileToDelete.getSize());
        dirService.updateDirsAfterFileUpdate(
                new VirtualPath(fileToDelete.getPath()),
                -fileToDelete.getSize(),
                new Date());
        fileRepo.delete(fileToDelete);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteFiles(List<UUID> filesIds) {
        for(var fileId : filesIds) {
            this.deleteFile(fileId);
        }
    }

    private FileEntity createFileEntity(CreateFileDTO fileInfo) {
        var fileDriveId = drivesService.getDriveIdAndReserveSpaceOnIt(fileInfo.size());
        var fileEntity = new FileEntity(
                dirRepo.findByPath(fileInfo.path().getParentDirectoryPath())
                        .orElseThrow(PathNotFoundException::noSuchDirectory),
                virtualDriveRepository.findByOwner_Username(fileInfo.path().getUsername())
                        .orElseThrow(IllegalStateException::new),
                driveRepository.getById(fileDriveId),
                fileInfo.path().getParentDirectoryPath() + fileInfo.path().getEntityName(),
                fileInfo.fileType() == null ?
                        FileType.UNDEFINED :
                        fileInfo.fileType(),
                fileInfo.size()
        );


        return fileRepo.saveAndFlush(fileEntity);
    }


}
