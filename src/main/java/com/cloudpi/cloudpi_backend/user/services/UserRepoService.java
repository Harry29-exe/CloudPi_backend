package com.cloudpi.cloudpi_backend.user.services;

import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import java.util.*;

public interface UserRepoService {

    List<UserDTO> getAllUsers();

    Optional<UserDTO> getUser(String username);
}