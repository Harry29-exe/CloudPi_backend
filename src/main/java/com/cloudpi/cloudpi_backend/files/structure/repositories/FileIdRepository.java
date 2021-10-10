package com.cloudpi.cloudpi_backend.files.structure.repositories;

import com.cloudpi.cloudpi_backend.files.structure.entities.FilesystemIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface FileIdRepository extends JpaRepository<FilesystemIdEntity, Long> {

}