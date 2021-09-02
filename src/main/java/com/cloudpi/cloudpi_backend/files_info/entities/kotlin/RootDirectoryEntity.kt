package com.cloudpi.cloudpi_backend.files_info.entities.kotlin

import com.cloudpi.cloudpi_backend.user.entities.UserEntity
import javax.persistence.Column
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

//@Entity
//@DiscriminatorValue(value = "FS_ROOT")
class RootDirectoryEntity(
    id: Long,
    owner: UserEntity,
    permissions: List<FilePermissionEntity>,

    @Column(nullable = false)
    var systemPath: String,

    ) : FilesystemIdEntity(
    id,
    owner,
    permissions
)