package com.cloudpi.cloudpi_backend.authorities.repositories;

import com.cloudpi.cloudpi_backend.authorities.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, String> {


    @Query("""
            SELECT r from RoleEntity r
            JOIN UserEntity u
            WHERE u.username = :username
            """)
    List<RoleEntity> findAllByRoleHolder(@Param("username") String roleHolderUsername);

    @Query("""
            SELECT r from RoleEntity r
            JOIN UserEntity u
            WHERE u.id = :id
            """)
    List<RoleEntity> findAllByRoleHolder(@Param("id") Long userId);

    @Query("SELECT new java.lang.String(r.role) from RoleEntity r JOIN r.roleHolder u WHERE u.username = :username ")
    List<String> getAllRolesByRoleHolder(@Param("username") String roleHolderUsername);

    @Query("SELECT new java.lang.String(r.role) from RoleEntity r JOIN r.roleHolder u WHERE u.id = :id")
    List<String> getAllRolesByRoleHolder(@Param("id") Long userId);

}
