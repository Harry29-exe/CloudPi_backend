package com.cloudpi.cloudpi_backend.files.filesystem.repositories;

import com.cloudpi.cloudpi_backend.files.filesystem.entities.PathEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
            WITH RECURSIVE cte (id, parent_id, object_type)
            AS (
                SELECT p.id, p.parent_id, p.object_type
                FROM paths p
                WHERE p.id = :id
                UNION ALL
                SELECT p.id, p.parent_id, p.object_type
                FROM paths p
                INNER JOIN cte c
                    ON p.id = c.parent_id
            )
            SELECT CAST(id AS VARCHAR) as id,
                    CAST(parent_id as VARCHAR) as parentId,
                    object_type as entityType
            FROM cte
            """, nativeQuery = true)
    Set<PathId> selectPathAndItsParentsIds(UUID id);


    @Query(value = """
            WITH RECURSIVE cte (id, parent_id, object_type)
            AS (
                SELECT p.id, p.parent_id, p.object_type
                FROM paths p
                WHERE p.id = :id
                UNION ALL
                SELECT p.id, p.parent_id, p.object_type
                FROM paths p
                INNER JOIN cte c
                    ON p.parent_id = c.id
            )
            SELECT CAST(id AS VARCHAR) as id,
                    CAST(parent_id as VARCHAR) as parentId,
                    object_type as entityType
            FROM cte
            """, nativeQuery = true)
    Set<PathId> selectPathAndItsChildrenIds(UUID id);

    interface PathId {

        UUID getId();

        UUID getParentId();

        char getEntityType();

    }
}
