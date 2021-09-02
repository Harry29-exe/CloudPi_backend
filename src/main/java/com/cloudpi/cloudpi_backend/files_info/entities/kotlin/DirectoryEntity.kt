package com.cloudpi.cloudpi_backend.files_info.entities.kotlin

import com.cloudpi.cloudpi_backend.user.entities.UserEntity
import java.util.*
import javax.persistence.*

//@Entity
//@DiscriminatorValue("DIRECTORY")
class DirectoryEntity(
    id: Long,
    owner: UserEntity,
    permissions: List<FilePermissionEntity>,
    name: String,
    relativePath: String,
    parent: FilesystemIdEntity,
    root: RootDirectoryEntity,
    createdAt: Date,
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var children: MutableList<FilesystemObjectEntity>,

    ) : FilesystemObjectEntity(id, owner, permissions, name, relativePath, parent, root, createdAt)