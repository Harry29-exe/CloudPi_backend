package com.cloudpi.cloudpi_backend.user.entities;

import com.cloudpi.cloudpi_backend.security.permissions.AccountType;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UserGrantedAuthorityEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String authority;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

//    public String getAuthority() {
//        return authority;
//    }

    //    public void setAuthority(String authority) {
//            for (var accountType : AccountType.values()) {
//                if (accountType.toString().equals(authority)) {
//                    throw new RuntimeException();
//                }
//            }
//
//            this.authority = authority;
//    }
}