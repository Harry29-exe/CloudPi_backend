package com.cloudpi.cloudpi_backend.files.structure.entities;

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
@DiscriminatorValue("DIRECTORY")
public class DirectoryEntity extends FilesystemObjectEntity {
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<FilesystemObjectEntity> children;
    @Column(nullable = false)
    private Date lastChildrenModification;
    @Column(nullable = false)
    private Long childrenSize;

}
