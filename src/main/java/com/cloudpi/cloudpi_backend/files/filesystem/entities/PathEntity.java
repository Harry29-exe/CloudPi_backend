package com.cloudpi.cloudpi_backend.files.filesystem.entities;

import com.cloudpi.cloudpi_backend.files.permissions.entities.DirectoryPermissionEntity;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "paths")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "object_type")
public class PathEntity {

    PathEntity(
                      @NotBlank String path,
                      DirectoryEntity parent,
                      @NonNull VirtualDriveEntity root,
                      @NonNull Long size,
                      @NonNull Date createdAt,
                      @NonNull Date modifiedAt
    ) {
        this.path = path;
        int lastSlash = path.lastIndexOf('/');
        this.name = lastSlash < 0?
                path:
                path.substring(lastSlash + 1);
        this.parent = parent;
        this.root = root;
        this.createdAt = createdAt;
        this.size = size;
        this.modifiedAt = modifiedAt;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public UUID id;

    @Column(nullable = false)
    private @NonNull String name;

    @Column(unique = true, nullable = false)
    private @NotBlank String path;

    /**
     * if it's null it means that the parents is root
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "parent_id", nullable = true)
    private DirectoryEntity parent;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "root_id", nullable = false)
    private @NonNull VirtualDriveEntity root;

    @Column(nullable = false)
    private @NonNull Long size;

    @Column(nullable = false)
    private @NonNull Date modifiedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private @NonNull Date createdAt;

    @OneToMany(
            cascade = {CascadeType.ALL},
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "permissions")
    @ToString.Exclude
    List<DirectoryPermissionEntity> permissions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathEntity that = (PathEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
