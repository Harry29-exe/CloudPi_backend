package com.cloudpi.cloudpi_backend.files.disk.repositories;

import com.cloudpi.cloudpi_backend.files.disk.entities.DriveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriveRepository extends JpaRepository<DriveEntity, Long> {
}
