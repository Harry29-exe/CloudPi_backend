package com.cloudpi.cloudpi_backend.user.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "user_details")
@Entity
@NoArgsConstructor
public class UserDetailsEntity {

    public UserDetailsEntity(@NonNull String nickname,
                             @NonNull AccountType accountType,
                             String email,
                             String pathToProfilePicture) {
        this.nickname = nickname;
        this.accountType = accountType;
        this.email = email;
        this.pathToProfilePicture = pathToProfilePicture;
    }

    @Column(nullable = false)
    private @NonNull String nickname;

    @Column(nullable = false, updatable = false)
    private @NonNull AccountType accountType = AccountType.USER;

    /**
     * optional: useful for changing, getting info about
     * account being set to be deleted
     */
    @Column
    private String email;

    @Column
    private String pathToProfilePicture;


    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
