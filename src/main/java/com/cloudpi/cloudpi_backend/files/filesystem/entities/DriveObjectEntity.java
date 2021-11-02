package com.cloudpi.cloudpi_backend.files.filesystem.entities;

import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@MappedSuperclass
public abstract class DriveObjectEntity {

    @Id
    @GeneratedValue(generator = "random-id")
    @GenericGenerator(
            name = "random-id",
            strategy = "com.cloudpi.cloudpi_backend.configuration.RandomLongIdGenerator"
    )
    @Column(name = "filesystem_id")
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @ToString.Exclude
    public UserEntity owner;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String relativePath;

    /**
     * if it's null it means that the parents is root
     */
    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = true)
    private DirectoryEntity parent;

    @ManyToOne
    @JoinColumn(name = "root_id", nullable = false)
    private VirtualDriveEntity root;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriveObjectEntity that = (DriveObjectEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
