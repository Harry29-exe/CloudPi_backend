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
    @Column(unique = true)
    private String role;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role_ownership")
    private List<UserEntity> authorised;

}
