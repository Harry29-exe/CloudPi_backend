package com.cloudpi.cloudpi_backend.files.filesystem.dto.mappers;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.FileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FileMapper {

    FileMapper INSTANCE = Mappers.getMapper(FileMapper.class);

    @Mapping(target = "parentId", expression = "java(entity.getParent().getId())")
    FileDto fileEntityToDTO(FileEntity entity);

}
