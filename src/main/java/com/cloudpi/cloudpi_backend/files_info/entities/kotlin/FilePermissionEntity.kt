package com.cloudpi.cloudpi_backend.files_info.entities.kotlin

import com.cloudpi.cloudpi_backend.files_info.pojo.FilePermissionType
import com.cloudpi.cloudpi_backend.user.entities.UserEntity
import javax.persistence.*

@Entity
class FilePermissionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(nullable = false)
    val permissionType: FilePermissionType,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    val user: UserEntity,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fs_id")
    val file: FilesystemIdEntity
)