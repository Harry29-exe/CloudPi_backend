package com.cloudpi.cloudpi_backend.files.physical.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "discs")
public class DiscEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private @NotBlank String pathToDisc;


//    @Column(nullable = false)
//    private @NotNull Long totalCapacity;

//    @Column(nullable = false)
//    private @NotNull Long freeSpace;

    @OneToMany(mappedBy = "disc")
    private List<DriveEntity> drives;

    public DiscEntity(String pathToDisc) {
        this.pathToDisc = pathToDisc;
    }
}
