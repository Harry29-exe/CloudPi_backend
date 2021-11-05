package com.cloudpi.cloudpi_backend.files.filesystem.entities;

import com.cloudpi.cloudpi_backend.files.disk.entities.DriveEntity;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.FileType;
import com.cloudpi.cloudpi_backend.files.permissions.entities.DriveObjectPermissionEntity;
import com.cloudpi.cloudpi_backend.files.permissions.entities.FilePermissionEntity;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "files")
public class FileEntity extends DriveObjectEntity {

    public FileEntity(@NonNull UserEntity owner,
                      DirectoryEntity parent,
                      @NonNull VirtualDriveEntity root,
                      @NonNull DriveEntity drive,
                      @NonNull String name,
                      @NotBlank String path,
                      @NonNull FileType fileType,
                      @NonNull Long size
    ) {
        super(owner, name, path, parent, root, new Date());
        this.size = size;
        this.modifiedAt = new Date();
        this.drive = drive;
        this.fileType = fileType;
    }

    @Column(nullable = false)
    private @NonNull Long size;

    @Column(nullable = false)
    private @NonNull Date modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "drive_files", nullable = false)
    private @NonNull DriveEntity drive;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private @NonNull FileType fileType = FileType.UNDEFINED;

    @OneToMany(
            cascade = {CascadeType.ALL},
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "permissions")
    @ToString.Exclude
    List<FilePermissionEntity> permissions;

}