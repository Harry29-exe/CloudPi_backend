package com.cloudpi.cloudpi_backend.files.disk.entities;

import com.cloudpi.cloudpi_backend.files.filesystem.entities.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "drives")
public class DriveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotBlank
    private String pathToDrive;

    @Column
    @Nullable
    private Long assignedSpace;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "disc_drive_relation")
    private DiscEntity disc;

    @OneToMany(cascade = CascadeType.REMOVE,
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "drive")
    private Set<FileEntity> files;


}
