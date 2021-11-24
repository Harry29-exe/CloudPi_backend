package com.cloudpi.cloudpi_backend.files.filesystem.services;

import com.cloudpi.cloudpi_backend.exepctions.files.PathNotFoundException;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDetailsDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDetailsDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileStructureDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileStructureDTO.FSDirectoryDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileStructureDTO.FSFileDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.DirectoryRepository;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.FileRepository;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.PathRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FilesystemInfoServiceImpl implements FilesystemInfoService {
    private final DirectoryRepository dirRepo;
    private final FileRepository fileRepo;
    private final PathRepository pathRepo;


    public FilesystemInfoServiceImpl(DirectoryRepository dirRepo, FileRepository fileRepo, PathRepository pathRepo) {
        this.dirRepo = dirRepo;
        this.fileRepo = fileRepo;
        this.pathRepo = pathRepo;
    }

    @Override
    public FileStructureDTO getFileStructure(Integer depth, VirtualPath rootDir) {
        var root = dirRepo.findByPath(rootDir.getPath())
                .orElseThrow(PathNotFoundException::noSuchDirectory);
        var childrenIds = pathRepo.selectPathAndItsChildrenIds(root.getId());

        FileStructureCreator structureCreator = new FileStructureCreator(childrenIds, root.getId(), depth);
        structureCreator.structureFiles();

        var structure = structureCreator.getStructureRoot();
        var filesDetails = fileRepo.findAllDetailsByIdIn(
                new HashSet<>(structureCreator.getFileFetchList()));
        var dirsDetails = dirRepo.findAllByIdIn(
                new HashSet<>(structureCreator.getDirFetchList()));

        var rootDirDto = FileStructureMapper.map(structure, dirsDetails, filesDetails);

        return new FileStructureDTO(depth, rootDir.getPath(), rootDirDto);
    }

    private static class FileStructureMapper {
        private final DirStructure dirStructure;
        private final Map<UUID, DirectoryDetailsDto> dirsInfo = new HashMap<>();
        private final Map<UUID, FileDetailsDto> filesInfo = new HashMap<>();

        private FSDirectoryDTO fsDirectoryDTO;

        FileStructureMapper(DirStructure dirStructure, List<DirectoryDetailsDto> dirsInfo, List<FileDetailsDto> filesInfo) {
            this.dirStructure = dirStructure;
            dirsInfo.forEach(d -> this.dirsInfo.put(d.getId(), d));
            filesInfo.forEach(f -> this.filesInfo.put(f.getId(), f));
            this.fsDirectoryDTO = new FSDirectoryDTO(dirStructure.id);
            mapFSDirectoryDTO(this.fsDirectoryDTO, dirStructure);
        }

        public static FSDirectoryDTO map(DirStructure dirStructure, List<DirectoryDetailsDto> dirsInfo, List<FileDetailsDto> filesInfo) {
            var fsMapper = new FileStructureMapper(dirStructure, dirsInfo, filesInfo);
            return fsMapper.fsDirectoryDTO;
        }

        public FSDirectoryDTO getFsDirectoryDTO() {
            return fsDirectoryDTO;
        }

        private void mapFSDirectoryDTO(FSDirectoryDTO directoryDTO, DirStructure dirStruct) {
            directoryDTO.setDetails(dirsInfo.get(dirStruct.id));
            for(var f : dirStruct.fileChildren) {
                directoryDTO.getFiles().add(new FSFileDTO(f, filesInfo.get(f)));
            }

            for(var d : dirStruct.dirChildren) {
                var dir = new FSDirectoryDTO(d.id);
                directoryDTO.getDirectories().add(dir);
                mapFSDirectoryDTO(dir, d);
            }
        }

    }

    private static class FileStructureCreator {
        private final Map<UUID, List<PathRepository.PathId>> parentChildrenMap;
        private final UUID rootDir;
        private final Integer maxLevel;

        private DirStructure structureRoot;
        private List<UUID> dirFetchList;
        private List<UUID> fileFetchList;

        public FileStructureCreator(Set<PathRepository.PathId> ids, UUID rootDirId, int maxLevel) {
            this.parentChildrenMap = createParentChildrenMap(ids);
            this.rootDir = rootDirId;
            this.maxLevel = maxLevel;
        }

        public void structureFiles() {
            dirFetchList = new ArrayList<>();
            fileFetchList = new ArrayList<>();

            structureRoot = new DirStructure(rootDir);
            dirFetchList.add(structureRoot.id);
            structureChildren(structureRoot, 0);
        }

        public List<UUID> getDirFetchList() {
            return dirFetchList;
        }

        public List<UUID> getFileFetchList() {
            return fileFetchList;
        }

        public DirStructure getStructureRoot() {
            return structureRoot;
        }

        private void structureChildren(DirStructure dir, int dirLevel) {
            var children = parentChildrenMap.get(dir.id);
            if(children == null) {
                return;
            }

            for (var child : children) {
                if (child.getEntityType() == 'D') {
                    dir.dirChildren.add(new DirStructure(child.getId()));
                    dirFetchList.add(child.getId());
                } else {
                    dir.fileChildren.add(child.getId());
                    fileFetchList.add(child.getId());
                }
            }

            if (maxLevel <= 0 || maxLevel < dirLevel + 1) {
                for (var childDir : dir.dirChildren) {
                    structureChildren(childDir, dirLevel + 1);
                }
            }
        }

        private Map<UUID, List<PathRepository.PathId>> createParentChildrenMap(Set<PathRepository.PathId> ids) {
            Map<UUID, List<PathRepository.PathId>> parentChildrenMap = new HashMap<>();
            for (var id : ids) {
                var childrenList = parentChildrenMap.get(id.getParentId());
                if (childrenList == null) {
                    parentChildrenMap.put(id.getParentId(), new ArrayList<>(List.of(id)));
                } else {
                    childrenList.add(id);
                }
            }

            return parentChildrenMap;
        }


    }

    private static class DirStructure {
        public UUID id;
        public List<DirStructure> dirChildren = new ArrayList<>();
        public List<UUID> fileChildren = new ArrayList<>();

        public DirStructure(UUID id) {
            this.id = id;
        }
    }

}
