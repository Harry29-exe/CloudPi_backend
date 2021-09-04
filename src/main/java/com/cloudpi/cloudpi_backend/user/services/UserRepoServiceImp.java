package com.cloudpi.cloudpi_backend.user.services;
;
import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserRepoServiceImp implements UserRepoService {

    @Override
    public List<UserDTO> getAllUsers() {
        return null;
    }

    @Override
    public Optional<UserDTO> getUser(String username) {
        return null;
    }
}