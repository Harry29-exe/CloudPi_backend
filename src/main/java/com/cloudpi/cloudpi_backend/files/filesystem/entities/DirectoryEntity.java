package com.cloudpi.cloudpi_backend.files.filesystem.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
@DiscriminatorValue("D")
public class DirectoryEntity extends PathEntity {

    public DirectoryEntity(
            DirectoryEntity parent,
            @NonNull VirtualDriveEntity root,
            @NonNull String path
    ) {
        super(path, parent, root, 0L, new Date(), new Date());
    }

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<DirectoryEntity> childrenDirectories;
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<FileEntity> childrenFiles;

}
