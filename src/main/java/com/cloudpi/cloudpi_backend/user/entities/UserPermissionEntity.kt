package com.cloudpi.cloudpi_backend.user.entities

import com.cloudpi.cloudpi_backend.security.AccountType
import org.hibernate.Hibernate
import javax.persistence.*

@Entity
public class UserPermissionEntity {
    private permission: String,
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: UserEntity

    @Id
    @GeneratedValue
    var id: Long = 0


    var permission: String = permission
        set(value) {
            for (accountType in AccountType.values()) {
                if (accountType.toString() == value) {
                    throw Exception()
                }
            }

            field = value
        }
}