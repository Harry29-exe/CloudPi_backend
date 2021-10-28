package com.cloudpi.cloudpi_backend.files.filesystem.entities;

import com.cloudpi.cloudpi_backend.files.disc.entities.DiscEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "root_directories")
public class RootDirectoryEntity {

    @Id
    @Column(name = "id")
    private Long id;
    @MapKey
    @JoinColumn
    @OneToOne
    private FilesystemIdEntity fsId;

    @Column(nullable = false)
    private String systemPath;
    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private DiscEntity disc;
    @Column(nullable = false)
    private Long assignedCapacity;
    @Column(nullable = false)
    private Long childrenSize;
}
