package com.cloudpi.cloudpi_backend.authorities.entities;

import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<UserEntity> roleHolder;

    public RoleEntity(String role, List<UserEntity> roleHolder) {
        this.role = role;
        this.roleHolder = roleHolder;
    }
}
