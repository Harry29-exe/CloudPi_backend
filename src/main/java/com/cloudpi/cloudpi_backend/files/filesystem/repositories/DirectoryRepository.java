package com.cloudpi.cloudpi_backend.files.filesystem.repositories;

import com.cloudpi.cloudpi_backend.files.filesystem.entities.DirectoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DirectoryRepository extends JpaRepository<DirectoryEntity, UUID> {

    @Transactional
    @Modifying
    @Query("""
            UPDATE DirectoryEntity d
            SET d.modifiedAt = :fileModifiedAt,
                d.size = d.size + :fileSizeDifference
            WHERE d.id IN :dirsIds
            """)
    void updateDirsAfterFileModification(
            Date fileModifiedAt,
            Long fileSizeDifference,
            List<UUID> dirsIds
    );

    @Query(value = "SELECT d.id FROM DirectoryEntity d WHERE d.relativePath = :path", nativeQuery = true)
    Long getIdOfPath(String path);

    Optional<DirectoryEntity> findByPath(String path);

}
