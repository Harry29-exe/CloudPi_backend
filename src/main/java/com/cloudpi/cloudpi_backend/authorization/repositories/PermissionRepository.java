package com.cloudpi.cloudpi_backend.authorization.repositories;

import com.cloudpi.cloudpi_backend.authorization.entities.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, String> {

    @Query("""
            SELECT r from PermissionEntity r
            JOIN UserEntity u
            WHERE u.username = :username
            """)
    List<PermissionEntity> findAllByAuthorised(@Param("username") String authorisedUsername);

    @Query("""
            SELECT r from PermissionEntity r
            JOIN UserEntity u
            WHERE u.id = :id
            """)
    List<PermissionEntity> findAllByAuthorised(@Param("id") Long authorisedId);

    @Query("""
            SELECT r.authority from PermissionEntity r
            JOIN r.authorised u
            WHERE u.username = :username
            """)
    List<String> getAuthoritiesByAuthorised(@Param("username") String authorisedUsername);


    @Query("""
            SELECT r.authority from PermissionEntity r
            JOIN r.authorised u
            WHERE u.id = :id
            """)
    List<String> getAuthoritiesByAuthorised(@Param("id") Long id);

}
