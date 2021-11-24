package com.cloudpi.cloudpi_backend.files.filesystem.repositories;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDetailsDto;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, UUID> {

    List<FileDetailsDto> findAllDetailsByIdIn(Iterable<UUID> ids);

    List<FileDto> findAllDtoByIdIn(Iterable<UUID> ids);

    Optional<FileEntity> findByPath(String path);

    Optional<FileDto> findDtoById(UUID id);

    Optional<FileDto> findDtoByPath(String path);

    Boolean existsByPath(String path);

    void deleteByPath(String path);

}