package com.cloudpi.cloudpi_backend.files_info.entities.kotlin

import com.cloudpi.cloudpi_backend.user.entities.UserEntity
import java.util.*
import javax.persistence.*

//@Entity
//@DiscriminatorValue(value = "FS_OBJECT")
abstract class FilesystemObjectEntity(
    id: Long,
    owner: UserEntity,
    permissions: List<FilePermissionEntity>,

    @Column(nullable = false)
    open var name: String,
    @Column(unique = true, nullable = false)
    open var relativePath: String,
    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    open var parent: FilesystemIdEntity,
    @ManyToOne
    @JoinColumn(name = "root_id", nullable = false)
    open var root: RootDirectoryEntity,
    @Column(nullable = false, updatable = false)
    open var createdAt: Date,


    ) : FilesystemIdEntity(
    id,
    owner,
    permissions
)