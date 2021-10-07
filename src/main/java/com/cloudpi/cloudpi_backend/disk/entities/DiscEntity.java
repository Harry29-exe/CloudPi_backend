package com.cloudpi.cloudpi_backend.disk.entities;

import com.cloudpi.cloudpi_backend.disk.pojo.DiscType;
import com.cloudpi.cloudpi_backend.files_info.entities.RootDirectoryEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DiscEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    @Column(nullable = false, unique = true)
    private String discName;
    @Column(unique = true)
    private String pathToDrives;
    @NotBlank
    @Column(nullable = false)
    private DiscType type;
    @NotNull
    @Column(nullable = false)
    private Long totalCapacity;
    @NotNull
    @Column(nullable = false)
    private Long freeSpace;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RootDirectoryEntity> usersDrives;
}
