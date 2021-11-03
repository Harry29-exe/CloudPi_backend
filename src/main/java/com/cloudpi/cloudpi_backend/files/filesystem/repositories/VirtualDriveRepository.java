package com.cloudpi.cloudpi_backend.files.filesystem.repositories;

import com.cloudpi.cloudpi_backend.files.filesystem.entities.VirtualDriveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VirtualDriveRepository extends JpaRepository<VirtualDriveEntity, Long> {

    Optional<VirtualDriveEntity> findByOwner_Id(Long id);

}