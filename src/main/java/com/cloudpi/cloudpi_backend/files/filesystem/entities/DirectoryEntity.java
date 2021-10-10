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
@SuperBuilder

@Entity
@Table(name = "directories")
@DiscriminatorValue("DIRECTORY")
public class DirectoryEntity extends DriveObjectEntity {
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<DriveObjectEntity> children;
    @Column(nullable = false)
    private Date lastChildrenModification;
    @Column(nullable = false)
    private Long childrenSize;

}
