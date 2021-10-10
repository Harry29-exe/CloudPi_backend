package com.cloudpi.cloudpi_backend.files.filesystem.entities;

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
@Table(name = "drive_objects")
@DiscriminatorValue(value = "DRIVE_OBJECT")
public class DriveObjectEntity extends DiscObjectIdEntity {
    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String relativePath;
    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    private DiscObjectIdEntity parent;
    @ManyToOne
    @JoinColumn(name = "root_id", nullable = false)
    private RootDirectoryEntity root;
    @Column(nullable = false, updatable = false)
    private Date createdAt;
}
