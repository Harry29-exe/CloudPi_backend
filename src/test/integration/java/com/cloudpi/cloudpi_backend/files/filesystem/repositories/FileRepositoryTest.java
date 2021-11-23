package com.cloudpi.cloudpi_backend.files.filesystem.repositories;

import com.cloudpi.cloudpi_backend.exepctions.files.PathNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class FileRepositoryTest {

    @Autowired
    FileRepository fileRepository;

    @Test
    void findDtoById() {
        var file = fileRepository.findAll().get(0);
        var dto = fileRepository.findDtoById(file.getId())
                .orElseThrow(PathNotFoundException::noSuchFile);
        assert dto.getId().equals(file.getId());
    }

    @Test
    void findDtoByPath() {
    }
}