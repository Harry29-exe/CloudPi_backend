package com.cloudpi.cloudpi_backend.user.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Table
@Entity
@NoArgsConstructor
public class UserDetailsEntity {

    public UserDetailsEntity(@NonNull String nickname, String email, String pathToProfilePicture) {
        this.nickname = nickname;
        this.email = email;
        this.pathToProfilePicture = pathToProfilePicture;
    }

    /**
     * For sending to other users in order to give opportunity
     * to share file with specific user
     */
    @Column(nullable = false, unique = true)
    private @NonNull String nickname;
    /**
     * Other option for logging
     */
    @Column(nullable = false)
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
