package com.cloudpi.cloudpi_backend.files.structure.repositories;

import com.cloudpi.cloudpi_backend.files.structure.entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface FileRepository extends JpaRepository<FileEntity, Long> {

}