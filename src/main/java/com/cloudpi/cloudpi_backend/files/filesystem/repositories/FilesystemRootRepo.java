package com.cloudpi.cloudpi_backend.files.filesystem.repositories;

import com.cloudpi.cloudpi_backend.files.filesystem.entities.VFilesystemRoot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilesystemRootRepo extends JpaRepository<VFilesystemRoot, Long> {

    Optional<VFilesystemRoot> findByOwner_Id(Long id);

    Optional<VFilesystemRoot> findByOwner_Username(String username);

}