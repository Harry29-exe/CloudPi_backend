package com.cloudpi.cloudpi_backend.files.filesystem.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "directories")
public class DirectoryEntity extends DriveObjectEntity {

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<DirectoryEntity> childrenDirectories;
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<FileEntity> childrenFiles;
    @Column(nullable = false)
    private Date lastChildrenModification;
    @Column(nullable = false)
    private Long childrenSize;
}
