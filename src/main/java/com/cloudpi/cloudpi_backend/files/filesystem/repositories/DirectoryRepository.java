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

    /**
     * @param fileModifiedAt     file creation/modification/deletion date
     * @param fileSizeDifference if file is created this is its size, if deleted it is its -size,
     *                           in case of file modification this is difference between current size
     *                           and size after modification
     * @param paths              paths to every parent of modified file so in case of file with path: username/dir1/dir2/file.ext
     *                           paths should be {username, username/dir1, username/dir2}
     */
    @Transactional
    @Modifying
    @Query("""
            UPDATE DirectoryEntity d
            SET d.modifiedAt = :fileModifiedAt,
                d.size = d.size + :fileSizeDifference
            WHERE d.path IN :paths
            """)
    void updateDirsAfterFileModification(
            Date fileModifiedAt,
            Long fileSizeDifference,
            List<String> paths
    );

    @Query(value = "SELECT d.id FROM DirectoryEntity d WHERE d.relativePath = :path", nativeQuery = true)
    Long getIdOfPath(String path);

    @Query("""
            SELECT COUNT(p.id)
            FROM PathEntity p
            WHERE p.parent = dirId
            """)
    Integer countChildren(UUID dirId);

    Optional<DirectoryEntity> findByPath(String path);

}
