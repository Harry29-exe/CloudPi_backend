package com.cloudpi.cloudpi_backend.files.filesystem.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;

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
}