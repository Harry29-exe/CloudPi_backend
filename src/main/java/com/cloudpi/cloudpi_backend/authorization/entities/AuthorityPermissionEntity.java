package com.cloudpi.cloudpi_backend.authorization.entities;

import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class AuthorityPermissionEntity {
    @Id
    @Column(unique = true, nullable = false)
    private String authority;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_permission_ownership")
    private Set<UserEntity> authorised;


}