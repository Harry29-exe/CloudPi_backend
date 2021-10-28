package com.cloudpi.cloudpi_backend.files.disk.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "discs")
public class DiscEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    @Column(nullable = false, unique = true)
    private String discName;
    @NotNull
    @Column(nullable = false)
    private Long totalCapacity;
    @NotNull
    @Column(nullable = false)
    private Long freeSpace;

    @OneToMany(mappedBy = "disc")
    private DriveEntity drive;
}
