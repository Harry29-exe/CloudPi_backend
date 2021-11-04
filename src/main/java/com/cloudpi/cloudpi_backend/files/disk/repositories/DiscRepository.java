package com.cloudpi.cloudpi_backend.files.disk.repositories;

import com.cloudpi.cloudpi_backend.files.disk.entities.DiscEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscRepository extends JpaRepository<DiscEntity, Long> {
}
