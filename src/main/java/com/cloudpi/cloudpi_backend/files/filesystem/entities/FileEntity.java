package com.cloudpi.cloudpi_backend.files.filesystem.entities;

import com.cloudpi.cloudpi_backend.files.filesystem.pojo.FileType;
import com.cloudpi.cloudpi_backend.files.permissions.entities.DriveObjectPermissionEntity;
import com.cloudpi.cloudpi_backend.files.permissions.entities.FilePermissionEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "files")
public class FileEntity extends DriveObjectEntity {

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false)
    private Date modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drive_files")
    private DirectoryEntity drive;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private FileType fileType = FileType.UNDEFINED;

    @OneToMany(
            cascade = {CascadeType.ALL},
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "permissions")
    @ToString.Exclude
    List<FilePermissionEntity> permissions;

}