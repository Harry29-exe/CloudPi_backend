package com.cloudpi.cloudpi_backend.authorization.repositories;

import com.cloudpi.cloudpi_backend.authorization.entities.AuthorityPermissionEntity;
import com.cloudpi.cloudpi_backend.authorization.entities.AuthorityRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<String, AuthorityRoleEntity> {
}
