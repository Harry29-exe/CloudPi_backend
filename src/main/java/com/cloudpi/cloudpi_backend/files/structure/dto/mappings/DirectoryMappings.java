package com.cloudpi.cloudpi_backend.files.structure.dto.mappings;

import com.cloudpi.cloudpi_backend.configuration.model_mapper.MapFunction;
import com.cloudpi.cloudpi_backend.files.structure.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.structure.entities.DirectoryEntity;
import org.modelmapper.ModelMapper;

public class DirectoryMappings implements MapFunction {

    @Override
    public void addMappingToModelMapper(ModelMapper modelMapper) {
        modelMapper.typeMap(DirectoryEntity.class, DirectoryDto.class);
    }
}