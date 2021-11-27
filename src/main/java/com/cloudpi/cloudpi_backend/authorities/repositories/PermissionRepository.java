package com.cloudpi.cloudpi_backend.authorities.repositories;

import com.cloudpi.cloudpi_backend.authorities.entities.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {

    Optional<PermissionEntity> findByAuthority(String authority);
//    @Query("""
//            SELECT r from PermissionEntity r
//            JOIN UserEntity u
//            WHERE u.username = :permissionOwner
//            """)
//    List<PermissionEntity> findAllByPermissionOwner(String permissionOwner);
//
//    @Query("""
//            SELECT r from PermissionEntity r
//            JOIN UserEntity u
//            WHERE u.id = :id
//            """)
//    List<PermissionEntity> findAllByPermissionOwner(@Param("id") Long authorisedId);
//
//    @Query("""
//            SELECT r.authority from PermissionEntity r
//            JOIN r.authorised u
//            WHERE u.id = :id
//            """)
//    List<String> getPermissionsByOwner(@Param("id") Long id);
//
//    @Transactional
//    @Modifying(flushAutomatically = true, clearAutomatically = true)
//    @Query(value = """
//            INSERT INTO users_permissions(user_id, permission_id)
//            VALUES (:userId, :permissionId)
//            """, nativeQuery = true)
//    void giveUserPermission(Long userId, Integer permissionId);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = """
            insert into users_permissions
            select u.id, p.id
            from users u
            join permissions p on (p.authority = :permissionName)
            where u.username = :username
            """, nativeQuery = true)
    void giveUserPermission(String username, String permissionName);

//    @Transactional
//    @Modifying(flushAutomatically = true, clearAutomatically = true)
//    @Query(value = """
//            DELETE FROM users_permissions up
//            WHERE up.user_id = :userId AND up.permission_id = :permissionId
//            """, nativeQuery = true)
//    void removeUsersPermission(Long userId, Integer permissionId);
//
//    @Transactional
//    @Modifying(flushAutomatically = true, clearAutomatically = true)
//    @Query(value = "delete from users_permissions up " +
//            "where up.permission_id = " +
//            "(select p.id  " +
//            "from permissions p " +
//            "where p.authority = :permissionName " +
//            ") " +
//            "and up.user_id = " +
//            "(select u.id  " +
//            "from users u " +
//            "where u.username = :username " +
//            ");", nativeQuery = true)
//    void removeUsersPermission(String username, String permissionName);
}
