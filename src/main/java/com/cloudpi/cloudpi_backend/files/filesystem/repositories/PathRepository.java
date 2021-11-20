package com.cloudpi.cloudpi_backend.files.filesystem.repositories;

import com.cloudpi.cloudpi_backend.files.filesystem.entities.PathEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PathRepository extends JpaRepository<PathEntity, UUID> {

    //TODO wytestować czy ostatnia linijka query zachowuje się tak jak powinna
    @Transactional
    @Modifying
    @Query("""
            UPDATE PathEntity p
            SET p.path = FUNCTION('regexp_replace', p.path, CONCAT('^', :oldPath), :newPath)
            WHERE p.root = :virtualDriveId
            """)
    void changeDirectoryPath(Long virtualDriveId, String oldPath, String newPath);


    @Query(value = """
            SELECT p.id
            FROM PathEntity p
            LEFT JOIN FETCH p.parent
            """)
    List<UUID> selectParentsIds(UUID id);

}
