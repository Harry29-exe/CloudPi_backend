package com.cloudpi.cloudpi_backend.files.filesystem.repositories;

import com.cloudpi.cloudpi_backend.files.filesystem.entities.PathEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PathRepository extends JpaRepository<PathEntity, UUID> {

    @Query("""
            UPDATE PathEntity p
            SET p.path = FUNCTION('regexp_replace', p.path, CONCAT('^', :oldPath), :to)
            """)
    void renameDirectory(String oldPath, String newPath);

}
