package com.cloudpi.cloudpi_backend.authorization.services;

import com.cloudpi.cloudpi_backend.authorization.dto.AuthorityDTO;
import com.cloudpi.cloudpi_backend.authorization.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthorityRepoServiceImp implements AuthorityRepoService {
    private RoleRepository repository;

    public AuthorityRepoServiceImp(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Set<AuthorityDTO> getUsersAuthorities() {
//        repository.
        return null;
    }

}
