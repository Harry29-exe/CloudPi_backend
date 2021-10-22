package com.cloudpi.cloudpi_backend.authorities.repositories;

import com.cloudpi.cloudpi_backend.authorities.entities.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "INSERT INTO users_permissions(user_id, permission_id) VALUES (:userId, :permissionId)", nativeQuery = true)
    void giveUserPermission(Long userId, Integer permissionId);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = """
            insert into users_permissions
            select u.id, p.id
            from users u
            join user_details ud on (u.id = ud.user_id)
            join permissions p on (p.authority = :permissionName)
            where ud.nickname = :nickname
            """, nativeQuery = true)
    void giveUserPermission(String nickname, String permissionName);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "DELETE FROM users_permissions up WHERE up.user_id = :userId AND up.permission_id = :permissionId", nativeQuery = true)
    void removeUsersPermission(Long userId, Integer permissionId);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "delete from users_permissions up " +
            "where up.permission_id = " +
            "(select p.id  " +
            "from permissions p " +
            "where p.authority = :permissionName " +
            ") " +
            "and up.user_id = " +
            "(select u.id  " +
            "from users u " +
            "join user_details ud on u.id = ud.user_id " +
            "where ud.nickname = :nickname " +
            ");", nativeQuery = true)
    void removeUsersPermission(String nickname, String permissionName);
}
