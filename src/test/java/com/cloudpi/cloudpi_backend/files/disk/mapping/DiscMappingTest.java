package com.cloudpi.cloudpi_backend.files.disk.mapping;

import com.cloudpi.cloudpi_backend.configuration.model_mapper.ModelMapperConfig;
import com.cloudpi.cloudpi_backend.files.disk.dto.DiscDTO;
import com.cloudpi.cloudpi_backend.files.disk.dto.UserDriveDTO;
import com.cloudpi.cloudpi_backend.files.disk.entities.DiscEntity;
import com.cloudpi.cloudpi_backend.files.structure.entities.RootDirectoryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static com.cloudpi.cloudpi_backend.utils.MappingUtils.testMapping;

public class DiscMappingTest {

    private final ModelMapperConfig modelMapperConfig = new ModelMapperConfig();
    private ModelMapper modelMapper;

    @BeforeEach
    void setUP() {
        modelMapper = new ModelMapper();
        modelMapperConfig.applyMappingsFromPackage(modelMapper, "com.cloudpi.cloudpi_backend.disc");
    }

    @Test
    void should_validate_mapping_DiscEntity_to_DiscDTO() {
        testMapping(modelMapper, DiscEntity.class, DiscDTO.class);
    }

    @Test
    void should_validate_mapping_DiscDTO_to_DiscEntity() {
        testMapping(modelMapper, DiscDTO.class, DiscEntity.class);
    }

    @Test
    void should_validate_mapping_RootDirectoryEntity_to_UserDriveDTO() {
        testMapping(modelMapper, RootDirectoryEntity.class, UserDriveDTO.class);
    }
}
