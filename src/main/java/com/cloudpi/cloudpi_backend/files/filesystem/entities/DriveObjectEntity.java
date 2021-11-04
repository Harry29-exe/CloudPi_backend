package com.cloudpi.cloudpi_backend.files.filesystem.entities;

import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor

@MappedSuperclass
public abstract class DriveObjectEntity {

    public DriveObjectEntity(@NonNull UserEntity owner,
                             @NonNull String name,
                             DirectoryEntity parent,
                             @NonNull VirtualDriveEntity root,
                             @NonNull Date createdAt) {
        this.owner = owner;
        this.name = name;
        this.parent = parent;
        this.root = root;
        this.createdAt = createdAt;
    }

    @Id
    @GeneratedValue(generator = "random-id")
    @GenericGenerator(
            name = "random-id",
            strategy = "com.cloudpi.cloudpi_backend.configuration.RandomLongIdGenerator"
    )
    @Column(name = "filesystem_id")
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    @ToString.Exclude
    public @NonNull UserEntity owner;

    @Column(nullable = false)
    private @NonNull String name;

    /**
     * if it's null it means that the parents is root
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "parent_id", nullable = true)
    private DirectoryEntity parent;

    @ManyToOne
    @JoinColumn(name = "root_id", nullable = false)
    private @NonNull VirtualDriveEntity root;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private @NonNull Date createdAt;

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
