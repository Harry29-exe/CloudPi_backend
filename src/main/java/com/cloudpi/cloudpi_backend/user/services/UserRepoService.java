package com.cloudpi.cloudpi_backend.user.services;

import com.cloudpi.cloudpi_backend.user.dto.UserDto;
import java.util.*;

public interface UserRepoService {

    List<UserDto> getAllUsers();

    Optional<UserDto> getUser(String username);
}