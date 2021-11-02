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
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "virtual_drives")
public class VirtualDriveEntity {

    @Id
    @GenericGenerator(
            name = "random-id",
            strategy = "com.cloudpi.cloudpi_backend.configuration.RandomLongIdGenerator"
    )
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private Long assignedCapacity;

    @Column(nullable = false)
    private Long childrenSize;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity owner;

    @OneToOne
    @JoinColumn(name = "root_directory")
    private DirectoryEntity rootDirectory;

}
