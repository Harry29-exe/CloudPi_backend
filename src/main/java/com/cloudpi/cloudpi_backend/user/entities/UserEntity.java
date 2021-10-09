package com.cloudpi.cloudpi_backend.user.entities;

import com.cloudpi.cloudpi_backend.files_info.entities.FilePermissionEntity;
import com.cloudpi.cloudpi_backend.files_info.entities.FilesystemObjectEntity;
import com.cloudpi.cloudpi_backend.user.controllers.AccountType;
import com.cloudpi.cloudpi_backend.authorization.entities.PermissionEntity;
import com.cloudpi.cloudpi_backend.authorization.entities.RoleEntity;
import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import com.cloudpi.cloudpi_backend.user.mappers.UserMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "app_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id = 0L;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
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
    private List<FilesystemObjectEntity> filesInfo;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<FilePermissionEntity> filesPermissions;

    public UserDTO toUserDTO() {
        return UserMapper.INSTANCE.userEntityToUserDTO(this);
    }
}