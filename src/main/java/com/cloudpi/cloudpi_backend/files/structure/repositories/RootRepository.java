package com.cloudpi.cloudpi_backend.files.structure.repositories;

import com.cloudpi.cloudpi_backend.files.structure.entities.RootDirectoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface RootRepository extends JpaRepository<RootDirectoryEntity, Long> {

}