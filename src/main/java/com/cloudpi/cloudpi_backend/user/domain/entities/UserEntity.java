package com.cloudpi.cloudpi_backend.user.domain.entities;

import com.cloudpi.cloudpi_backend.files.filesystem.entities.VFilesystemRoot;
import com.cloudpi.cloudpi_backend.security.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    public UserEntity(@NonNull String username,
                      @NonNull String password,
                      @NonNull UserDetailsEntity userDetails,
                      Set<Role> roles) {

        this.username = username;
        this.password = password;
        this.userDetails = userDetails;
        this.userDetails.setUser(this);
        this.roles = roles;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false)
    private Long id;

    /**
     * For sending to other users in order to give opportunity
     * to share file with specific user
     */
    @Column(nullable = false, unique = true, updatable = false)
    private @NotBlank String username;
    @Column(nullable = false)
    private @NotBlank String password;
    @Column(nullable = false)
    private @NonNull Boolean locked = false;

    @PrimaryKeyJoinColumn
    @OneToOne(mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private @NonNull UserDetailsEntity userDetails;


    @OneToOne(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private VFilesystemRoot userDrive;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id.equals(that.id) && username.equals(that.username) && password.equals(that.password) && locked.equals(that.locked) && userDetails.equals(that.userDetails) && Objects.equals(userDeleteSchedule, that.userDeleteSchedule) && Objects.equals(roles, that.roles) && Objects.equals(permissions, that.permissions);
    }

}