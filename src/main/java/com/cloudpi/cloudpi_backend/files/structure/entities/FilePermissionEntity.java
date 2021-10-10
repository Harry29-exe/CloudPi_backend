package com.cloudpi.cloudpi_backend.files.structure.entities;


import com.cloudpi.cloudpi_backend.files.structure.pojo.FilePermissionType;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FilePermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private FilePermissionType permissionType;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fs_id")
    private FilesystemIdEntity file;
}