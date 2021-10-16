package com.cloudpi.cloudpi_backend.user.entities;

import com.cloudpi.cloudpi_backend.authorities.entities.PermissionEntity;
import com.cloudpi.cloudpi_backend.authorities.entities.RoleEntity;
import com.cloudpi.cloudpi_backend.exepctions.user.endpoint.InvalidUserData;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.DriveObjectEntity;
import com.cloudpi.cloudpi_backend.files.permissions.entities.FilePermissionEntity;
import com.cloudpi.cloudpi_backend.user.enpoints.AccountType;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import com.cloudpi.cloudpi_backend.user.mappers.UserMapper;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(name = "user_id")
    private Long id = 0L;
    /**
     * For logging
     */
    @Column(nullable = false, unique = true)
    private String username;
    /**
     * Other option for logging
     */
    @Column(nullable = false)
    private String email;
    /**
     * For sending to other users in order to give opportunity
     * to share file with specific user
     */
    @Column(nullable = false, unique = true)
    private String nickname;
    @NotBlank
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Boolean locked = false;
    @Column(nullable = false, updatable = false)
    private String accountType = AccountType.USER;

    @ManyToMany(mappedBy = "roleHolder", cascade = CascadeType.MERGE)
    private Set<RoleEntity> roles;
    @ManyToMany(mappedBy = "authorised", cascade = CascadeType.MERGE)
    private Set<PermissionEntity> permissions;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<DriveObjectEntity> filesInfo;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<FilePermissionEntity> filesPermissions;

    @PrePersist
    public void validateUser() {
        if(nickname.equals(username)) {
            throw new InvalidUserData("Username and nickname must be different");
        }
    }

    public UserWithDetailsDTO toUserDTO() {
        return UserMapper.INSTANCE.userEntityToUserDTO(this);
    }
}