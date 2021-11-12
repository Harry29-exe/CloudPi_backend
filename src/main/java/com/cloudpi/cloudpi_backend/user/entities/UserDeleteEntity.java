package com.cloudpi.cloudpi_backend.user.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter

@Table(name = "user_delete_schedule")
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
