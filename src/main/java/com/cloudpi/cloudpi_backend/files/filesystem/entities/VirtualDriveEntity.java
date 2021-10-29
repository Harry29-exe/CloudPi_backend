package com.cloudpi.cloudpi_backend.files.filesystem.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "virtual_drives")
public class VirtualDriveEntity {

    @Id
    @Column(name = "id")
    private Long id;
    @MapKey
    @JoinColumn
    @OneToOne
    private FilesystemIdEntity fsId;

    @Column(nullable = false)
    private Long assignedCapacity;
    @Column(nullable = false)
    private Long childrenSize;
}
