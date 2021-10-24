package com.cloudpi.cloudpi_backend.user.entities;

import com.cloudpi.cloudpi_backend.authorities.entities.PermissionEntity;
import com.cloudpi.cloudpi_backend.authorities.entities.RoleEntity;
import com.cloudpi.cloudpi_backend.exepctions.user.endpoint.InvalidUserData;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.DriveObjectEntity;
import com.cloudpi.cloudpi_backend.files.permissions.entities.FilePermissionEntity;
import com.cloudpi.cloudpi_backend.user.dto.UserPublicIdDTO;
import com.cloudpi.cloudpi_backend.user.enpoints.AccountType;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import com.cloudpi.cloudpi_backend.user.mappers.UserMapper;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false)
    private Long id;
    /**
     * For logging
     */
    @Column(nullable = false, unique = true)
    private @NonNull @NotBlank String login;
    @Column(nullable = false, unique = true)
    private @NonNull String username;
    @Column(nullable = false)
    private @NonNull @NotBlank String password;
    @Column(nullable = false)
    private @NonNull Boolean locked = false;
    @Column(nullable = false, updatable = false)
    private @NonNull @NotBlank String accountType = AccountType.user;

    @PrimaryKeyJoinColumn
    @OneToOne(mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private @NonNull UserDetailsEntity userDetails;

    @PrimaryKeyJoinColumn
    @OneToOne(
            mappedBy = "userToBeDeleted",
            cascade = {CascadeType.MERGE, CascadeType.REMOVE},
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private @Nullable UserDeleteEntity userDeleteSchedule;


    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;
    @ManyToMany
    @JoinTable(
            name = "users_permissions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<PermissionEntity> permissions;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<DriveObjectEntity> usersDrives;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<FilePermissionEntity> filesPermissions;

    @PrePersist
    @PreUpdate
    public void validateUser() {
        if(username.equals(login)) {
            throw new InvalidUserData("Username and nickname must be different");
        }
    }

    public UserWithDetailsDTO toUserWithDetailsDTO() {
        return UserMapper.INSTANCE.userEntityToUserWithDetailsDTO(this);
    }

    public UserPublicIdDTO toUserPublicIdDTO() {
        return UserMapper.INSTANCE.userEntityToUserPublicIdDTO(this);
    }
}