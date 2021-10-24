package com.cloudpi.cloudpi_backend.user.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "user_details")
@Entity
@NoArgsConstructor
public class UserDetailsEntity {

    public UserDetailsEntity(String email, String pathToProfilePicture) {
        this.email = email;
        this.pathToProfilePicture = pathToProfilePicture;
    }

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
