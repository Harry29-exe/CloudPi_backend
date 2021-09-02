package com.cloudpi.cloudpi_backend.user.entities;

import com.cloudpi.cloudpi_backend.files_info.entities.kotlin.FilesystemObjectEntity;
import com.cloudpi.cloudpi_backend.files_info.entities.kotlin.FilePermissionEntity;
import com.cloudpi.cloudpi_backend.security.AccountType;
import lombok.*;
import org.hibernate.Hibernate;
import javax.persistence.*;
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
    private String username;
    private String email;
    private String nickname;
    private String password;
    private Boolean locked;
    private AccountType accountType;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<UserPermissionEntity> permissions;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<FilesystemObjectEntity> files_info;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<FilePermissionEntity> filesPermissions;

}