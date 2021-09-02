package com.cloudpi.cloudpi_backend.files_info.entities.kotlin

import com.cloudpi.cloudpi_backend.user.entities.UserEntity
import java.util.*
import javax.persistence.Column
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

//@Entity
//@DiscriminatorValue("FILE")
class FileEntity(
    id: Long,
    owner: UserEntity,
    permissions: List<FilePermissionEntity>,
    name: String,
    relativePath: String,
    parent: FilesystemIdEntity,
    root: RootDirectoryEntity,
    createdAt: Date,
    @Column(nullable = false)
    var size: Long,
    @Column(nullable = false)
    var modifiedAt: Date,


    ) : FilesystemObjectEntity(id, owner, permissions, name, relativePath, parent, root, createdAt)