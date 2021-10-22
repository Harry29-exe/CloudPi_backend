package com.cloudpi.cloudpi_backend.authorities.entities;

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
@Table(name = "permissions")
public class PermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true, nullable = false, updatable = false)
    private String authority;
    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private Set<UserEntity> authorised;

    public PermissionEntity(String authority, Set<UserEntity> authorised) {
        this.authority = authority;
        this.authorised = authorised;
    }
}