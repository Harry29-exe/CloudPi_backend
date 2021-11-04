package com.cloudpi.cloudpi_backend.files.filesystem.entities;

import com.cloudpi.cloudpi_backend.files.permissions.entities.DirectoryPermissionEntity;
import com.cloudpi.cloudpi_backend.files.permissions.entities.DriveObjectPermissionEntity;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor()
@NoArgsConstructor

@Entity
@Table(name = "directories")
public class DirectoryEntity extends DriveObjectEntity {

    public DirectoryEntity(@NonNull UserEntity owner,
                           DirectoryEntity parent,
                           @NonNull VirtualDriveEntity root,
                           @NonNull String name,
                           @NonNull String path
    ) {
        super(owner, name, parent, root, new Date());
        this.path = path;
        this.lastChildrenModification = new Date();
    }

    @Column(unique = true, nullable = false)
    private @NonNull String path;
    @Column(nullable = false)
    private @NonNull Date lastChildrenModification;
    @Column(nullable = false)
    private @NonNull Long childrenSize = 0L;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<DirectoryEntity> childrenDirectories;
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<FileEntity> childrenFiles;

    @OneToMany(
            cascade = {CascadeType.ALL},
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "permissions")
    @ToString.Exclude
    List<DirectoryPermissionEntity> permissions;

}
