package com.cloudpi.cloudpi_backend.files.filesystem.repositories;

import com.cloudpi.cloudpi_backend.files.filesystem.entities.DirectoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DirectoryRepository extends JpaRepository<DirectoryEntity, Long> {

    @Query("SELECT d.id FROM DirectoryEntity d WHERE d.relativePath = :path")
    Long getIdOfPath(String path);

    Optional<DirectoryEntity> findByPath(String path);

}
