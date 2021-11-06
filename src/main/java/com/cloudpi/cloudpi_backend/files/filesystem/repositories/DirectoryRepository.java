package com.cloudpi.cloudpi_backend.files.filesystem.repositories;

import com.cloudpi.cloudpi_backend.files.filesystem.entities.DirectoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DirectoryRepository extends JpaRepository<DirectoryEntity, UUID> {

    @Query(value = "SELECT d.id FROM DirectoryEntity d WHERE d.relativePath = :path", nativeQuery = true)
    Long getIdOfPath(String path);

    Optional<DirectoryEntity> findByPath(String path);

}
