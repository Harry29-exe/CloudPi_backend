package com.cloudpi.cloudpi_backend.authorities.entities;

import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true, updatable = false, nullable = false)
    private String role;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST)
    private Set<UserEntity> roleHolder = new HashSet<>();

    public RoleEntity(String role, Set<UserEntity> roleHolder) {
        this.role = role;
        this.roleHolder = roleHolder;
    }
}
