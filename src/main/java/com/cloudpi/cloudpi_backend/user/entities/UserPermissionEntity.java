package com.cloudpi.cloudpi_backend.user.entities;

import com.cloudpi.cloudpi_backend.security.AccountType;
import lombok.*;
import org.hibernate.Hibernate;
import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UserPermissionEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String permission;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;



    public void setPermission(String permission) {
            for (var accountType : AccountType.values()) {
                if (accountType.toString().equals(permission)) {
                    throw new RuntimeException();
                }
            }

            this.permission = permission;
    }
}