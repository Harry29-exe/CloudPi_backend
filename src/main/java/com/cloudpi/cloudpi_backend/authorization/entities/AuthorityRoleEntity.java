package com.cloudpi.cloudpi_backend.authorization.entities;

import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityRoleEntity {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String role;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<UserEntity> authorised;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AuthorityPermissionEntity> permissions;
}
