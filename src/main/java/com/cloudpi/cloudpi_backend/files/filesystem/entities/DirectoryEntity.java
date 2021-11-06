package com.cloudpi.cloudpi_backend.files.filesystem.entities;

import com.cloudpi.cloudpi_backend.files.permissions.entities.DirectoryPermissionEntity;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
@DiscriminatorValue("DIR")
public class DirectoryEntity extends PathEntity {

    public DirectoryEntity(@NonNull UserEntity owner,
                           DirectoryEntity parent,
                           @NonNull VirtualDriveEntity root,
                           @NonNull String name,
                           @NonNull String path
    ) {
        super(owner, name, path, parent, root, 0L, new Date(), new Date());
    }

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<DirectoryEntity> childrenDirectories;
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<FileEntity> childrenFiles;

}
