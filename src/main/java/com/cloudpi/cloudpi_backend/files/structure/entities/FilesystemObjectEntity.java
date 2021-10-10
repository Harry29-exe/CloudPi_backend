package com.cloudpi.cloudpi_backend.files.structure.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
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
