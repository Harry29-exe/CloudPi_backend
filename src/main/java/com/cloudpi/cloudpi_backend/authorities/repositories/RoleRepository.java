package com.cloudpi.cloudpi_backend.authorities.repositories;

import com.cloudpi.cloudpi_backend.authorities.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, String> {


    @Query("""
            SELECT r from RoleEntity r
            JOIN UserEntity u
            WHERE u.username = :roleHolderUsername
            """)
    List<RoleEntity> findAllByRoleHolder(String roleHolderUsername);

    @Query("""
            SELECT r from RoleEntity r
            JOIN UserEntity u
            WHERE u.id = :id
            """)
    List<RoleEntity> findAllByRoleHolder(@Param("id") Long userId);

    @Query("""
            SELECT new java.lang.String(r.role)
            FROM RoleEntity r JOIN r.roleHolder u
            WHERE u.username = :roleHolderUsername
            """)
    List<String> getAllRolesByRoleHolder(String roleHolderUsername);

    @Query("SELECT new java.lang.String(r.role) from RoleEntity r JOIN r.roleHolder u WHERE u.id = :id")
    List<String> getAllRolesByRoleHolder(@Param("id") Long userId);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "INSERT INTO users_roles VALUES (:userId, :roleId)", nativeQuery = true)
    void giveUserRole(Long userId, Integer roleId);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = """
            insert into users_roles
            select u.id, r.id
            from users u
            join roles r on (r.role = :roleName)
            where u.username = :username
            """, nativeQuery = true)
    void giveUserRole(String username, String roleName);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "DELETE FROM users_roles ur WHERE ur.user_id = :userId AND ur.role_id = :roleId", nativeQuery = true)
    void removeUsersRole(Long userId, Integer roleId);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "delete from users_roles ur " +
            "where ur.role_id = " +
            "(select r.id  " +
            "from proles r " +
            "where r.role = :roleName " +
            ") " +
            "and ur.user_id = " +
            "(select u.id  " +
            "from users u " +
            "where u.username = :username " +
            ");", nativeQuery = true)
    void removeUsersPermission(String username, String roleName);
}
