package com.cloudpi.cloudpi_backend.authorization.repositories;

import com.cloudpi.cloudpi_backend.authorization.entities.AuthorityPermissionEntity;
import com.cloudpi.cloudpi_backend.authorization.entities.AuthorityRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<AuthorityRoleEntity, String> {


    @Query("""
            SELECT r from AuthorityRoleEntity r
            JOIN UserEntity u
            WHERE u.username = :username
            """)
    List<AuthorityRoleEntity> findAllByRoleHolder(@Param("username") String roleHolderUsername);

    @Query("""
            SELECT r from AuthorityRoleEntity r
            JOIN UserEntity u
            WHERE u.id = :id
            """)
    List<AuthorityRoleEntity> findAllByRoleHolder(@Param("id") Long userId);

    @Query("SELECT new java.lang.String(r.role) from AuthorityRoleEntity r JOIN r.roleHolder u WHERE u.username = :username ")
    List<String> getAllRolesByRoleHolder(@Param("username") String roleHolderUsername);

    @Query("SELECT new java.lang.String(r.role) from AuthorityRoleEntity r JOIN r.roleHolder u WHERE u.id = :id")
    List<String> getAllRolesByRoleHolder(@Param("id") Long userId);

}
