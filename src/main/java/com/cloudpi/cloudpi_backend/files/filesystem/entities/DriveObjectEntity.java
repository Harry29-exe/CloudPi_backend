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

@MappedSuperclass
//@Entity
//@Table(name = "drive_object")
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class DriveObjectEntity {

    @Id
    @Column(name = "id")
    private Long id;
    @MapKey
    @JoinColumn(nullable = false)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private FilesystemIdEntity fsId;

    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String relativePath;
    /**
     * if it's null it means that the parents is root
     */
    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = true)
    private DirectoryEntity parent;
    @ManyToOne
    @JoinColumn(name = "root_id", nullable = false)
    private VirtualDriveEntity root;
    @Column(nullable = false, updatable = false)
    private Date createdAt;
}
