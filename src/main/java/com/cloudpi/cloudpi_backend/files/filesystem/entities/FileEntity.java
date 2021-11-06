package com.cloudpi.cloudpi_backend.files.filesystem.entities;

import com.cloudpi.cloudpi_backend.files.physical.entities.DriveEntity;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.FileType;
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
@DiscriminatorValue("FILE")
public class FileEntity extends PathEntity {

    public FileEntity(@NonNull UserEntity owner,
                      @NonNull DirectoryEntity parent,
                      @NonNull VirtualDriveEntity root,
                      @NonNull DriveEntity drive,
                      @NonNull String name,
                      @NotBlank String path,
                      @NonNull FileType fileType,
                      @NonNull Long size
    ) {
        super(owner, name, path, parent, root, size, new Date(), new Date());
        this.drive = drive;
        this.fileType = fileType;
    }



    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "drive_files")
    private @NonNull DriveEntity drive;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private @NonNull FileType fileType = FileType.UNDEFINED;

}