package com.cloudpi.cloudpi_backend.files.physical.entities;

import com.cloudpi.cloudpi_backend.files.filesystem.entities.FileEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * Represents folder on physical disc where
 * files from cloud are saved
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "drives")
public class DriveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public DriveEntity(String pathToDrive, Long assignedSpace, DiscEntity disc) {
        this.pathToDrive = pathToDrive;
        this.assignedSpace = assignedSpace;
        this.disc = disc;
        this.spaceInUse = 0L;
    }

    @Column(unique = true)
    @NotBlank
    private String pathToDrive;

    @Column(nullable = false)
    private Long assignedSpace;

    @Column(nullable = false)
    private Long spaceInUse;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "disc", nullable = false)
    private DiscEntity disc;

    @OneToMany(cascade = CascadeType.REMOVE,
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "drive")
    private Set<FileEntity> files;


}
