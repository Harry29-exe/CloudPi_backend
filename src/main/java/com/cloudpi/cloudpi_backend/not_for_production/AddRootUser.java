package com.cloudpi.cloudpi_backend.not_for_production;

import com.cloudpi.cloudpi_backend.user.enpoints.AccountType;
import com.cloudpi.cloudpi_backend.user.entities.UserDetailsEntity;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AddRootUser {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initRootUser() {
        UserEntity userEntity = new UserEntity(
                "root",
                passwordEncoder.encode("123"),
                new UserDetailsEntity("mighty root", "root@cloud.pl", null)
        );
        userEntity.getUserDetails().setUser(userEntity);

        userRepository.save(userEntity);
    }
}
