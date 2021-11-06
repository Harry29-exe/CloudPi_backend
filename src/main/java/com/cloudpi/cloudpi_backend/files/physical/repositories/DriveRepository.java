package com.cloudpi.cloudpi_backend.files.physical.repositories;

import com.cloudpi.cloudpi_backend.files.physical.entities.DriveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriveRepository extends JpaRepository<DriveEntity, Long> {
}
