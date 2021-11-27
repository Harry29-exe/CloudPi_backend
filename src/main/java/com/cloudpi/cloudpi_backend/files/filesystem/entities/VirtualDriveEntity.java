package com.cloudpi.cloudpi_backend.files.filesystem.entities;

import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "virtual_drives")
public class VirtualDriveEntity {

    public VirtualDriveEntity(Long assignedCapacity, UserEntity owner) {
        this.assignedCapacity = assignedCapacity;
        this.owner = owner;
        owner.setUserDrive(this);
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private Long assignedCapacity;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, updatable = false)
    private UserEntity owner;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "root_directory")
    private DirectoryEntity rootDirectory;

    @PrePersist
    void checkIdRootDirectoryNotNull() {
        if (rootDirectory == null) {
            throw new IllegalStateException("Virtual drive must have assigned root directory");
        }
    }

    public void setRootDirectory(DirectoryEntity rootDirectory) {
        this.rootDirectory = rootDirectory;
        rootDirectory.setRoot(this);
    }
}
