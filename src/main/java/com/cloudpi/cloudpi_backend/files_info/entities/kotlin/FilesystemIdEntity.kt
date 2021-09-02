package com.cloudpi.cloudpi_backend.files_info.entities.kotlin

import com.cloudpi.cloudpi_backend.user.entities.UserEntity
import javax.persistence.*

//@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "fs_type", discriminatorType = DiscriminatorType.STRING)
abstract class FilesystemIdEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fs_id", nullable = false)
    open var id: Long,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    open var owner: UserEntity,
    @OneToMany(
        mappedBy = "file",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    open var permissions: List<FilePermissionEntity>
)