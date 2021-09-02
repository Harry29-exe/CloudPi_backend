package com.cloudpi.cloudpi_backend.files_info.entities.java;

import com.cloudpi.cloudpi_backend.files_info.entities.kotlin.RootDirectoryEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue(value = "FS_OBJECT")
public class FilesystemObjectEntity extends FilesystemIdEntity {
    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String relativePath;
    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    private FilesystemIdEntity parent;
    @ManyToOne
    @JoinColumn(name = "root_id", nullable = false)
    private RootDirectoryEntity root;
    @Column(nullable = false, updatable = false)
    private Date createdAt;
}
