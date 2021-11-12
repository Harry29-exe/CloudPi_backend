package com.cloudpi.cloudpi_backend.user.entities;

import com.cloudpi.cloudpi_backend.authorities.entities.PermissionEntity;
import com.cloudpi.cloudpi_backend.authorities.entities.RoleEntity;
import com.cloudpi.cloudpi_backend.exepctions.user.endpoint.InvalidUserData;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.VirtualDriveEntity;
import com.cloudpi.cloudpi_backend.user.dto.AccountType;
import com.cloudpi.cloudpi_backend.user.dto.UserPublicIdDTO;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import com.cloudpi.cloudpi_backend.user.mappers.UserMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    public UserEntity(@NonNull String login,
                      @NonNull String username,
                      @NonNull String password,
                      @NonNull AccountType accountType,
                      @NonNull UserDetailsEntity userDetails,
                      Set<RoleEntity> roles,
                      Set<PermissionEntity> permissions) {
        this.login = login;
        this.username = username;
        this.password = password;
        this.accountType = accountType;
        this.userDetails = userDetails;
        this.userDetails.setUser(this);
        this.roles = roles;
        this.permissions = permissions;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false)
    private Long id;
    /**
     * For logging
     */
    @Column(nullable = false, unique = true)
    private @NonNull @NotBlank String login;
    /**
     * For sending to other users in order to give opportunity
     * to share file with specific user
     */
    @Column(nullable = false, unique = true)
    private @NonNull @NotBlank String username;
    @Column(nullable = false)
    private @NonNull @NotBlank String password;
    @Column(nullable = false)
    private @NonNull Boolean locked = false;
    @Column(nullable = false, updatable = false)
    private @NonNull AccountType accountType = AccountType.USER;

    @PrimaryKeyJoinColumn
    @OneToOne(mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
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
    private @Nullable
    UserDeleteEntity userDeleteSchedule;

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

    @OneToOne(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private VirtualDriveEntity userDrive;

    @PrePersist
    @PreUpdate
    public void validateUser() {
        if (username.equals(login)) {
            throw new InvalidUserData("Username and nickname must be different");
        }
    }

    public UserWithDetailsDTO toUserWithDetailsDTO() {
        return UserMapper.INSTANCE.userEntityToUserWithDetailsDTO(this);
    }

    public UserPublicIdDTO toUserPublicIdDTO() {
        return UserMapper.INSTANCE.userEntityToUserPublicIdDTO(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id.equals(that.id) && login.equals(that.login) && username.equals(that.username) && password.equals(that.password) && locked.equals(that.locked) && accountType == that.accountType && userDetails.equals(that.userDetails) && Objects.equals(userDeleteSchedule, that.userDeleteSchedule) && Objects.equals(roles, that.roles) && Objects.equals(permissions, that.permissions);
    }

}