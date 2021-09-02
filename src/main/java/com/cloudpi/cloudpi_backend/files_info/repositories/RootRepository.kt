package com.cloudpi.cloudpi_backend.files_info.repositories

import com.cloudpi.cloudpi_backend.files_info.entities.kotlin.RootDirectoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RootRepository : JpaRepository<RootDirectoryEntity, Long>