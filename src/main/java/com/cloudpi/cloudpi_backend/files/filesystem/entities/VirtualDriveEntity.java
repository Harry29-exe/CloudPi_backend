package com.cloudpi.cloudpi_backend.files.filesystem.entities;

import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

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

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "root_directory")
    private DirectoryEntity rootDirectory;

    @PrePersist
    void checkIdRootDirectoryNotNull() {
        if(rootDirectory == null) {
            throw new IllegalStateException("Virtual drive must have assigned root directory");
        }
    }

}
