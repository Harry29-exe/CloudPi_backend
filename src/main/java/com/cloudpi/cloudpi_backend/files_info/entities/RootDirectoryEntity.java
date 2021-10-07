package com.cloudpi.cloudpi_backend.files_info.entities;

import com.cloudpi.cloudpi_backend.disk.entities.DiscEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@DiscriminatorValue("DIRECTORY")
public class RootDirectoryEntity extends FilesystemIdEntity {
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
