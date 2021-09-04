package com.cloudpi.cloudpi_backend.user.entities;

import com.cloudpi.cloudpi_backend.files_info.entities.FilePermissionEntity;
import com.cloudpi.cloudpi_backend.files_info.entities.FilesystemObjectEntity;
import com.cloudpi.cloudpi_backend.security.permissions.AccountType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "app_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @NotBlank
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
    private Boolean locked;
    private AccountType accountType;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<UserGrantedAuthorityEntity> permissions;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<FilesystemObjectEntity> files_info;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<FilePermissionEntity> filesPermissions;

}