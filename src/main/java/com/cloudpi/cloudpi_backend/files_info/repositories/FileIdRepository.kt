package com.cloudpi.cloudpi_backend.files_info.repositories

import com.cloudpi.cloudpi_backend.files_info.entities.kotlin.FilesystemIdEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FileIdRepository : JpaRepository<FilesystemIdEntity, Long>