package com.cloudpi.cloudpi_backend.user.services;
;
import com.cloudpi.cloudpi_backend.user.dto.UserDto;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserRepoServiceImp implements UserRepoService {

    @Override
    public List<UserDto> getAllUsers() {
        return null;
    }

    @Override
    public Optional<UserDto> getUser(String username) {
        return null;
    }
}