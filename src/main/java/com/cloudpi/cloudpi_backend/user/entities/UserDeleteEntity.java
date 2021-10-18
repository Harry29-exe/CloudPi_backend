package com.cloudpi.cloudpi_backend.user.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter

@Table
@Entity
public class UserDeleteEntity {

    //TODO zobaczyć czy działa bez id
    public UserDeleteEntity() {
        this.deleteScheduledAt = new Date();
    }


    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, insertable = false)
    private Date deleteScheduledAt;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity userToBeDeleted;
}
