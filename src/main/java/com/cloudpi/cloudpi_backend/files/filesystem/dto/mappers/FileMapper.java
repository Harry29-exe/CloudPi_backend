package com.cloudpi.cloudpi_backend.files.filesystem.dto.mappers;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.FileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface FileMapper {

    @Mapping(target = "parentId", expression = "java(entity.getParent().getId())")
    @Mapping(target = "fileName", source = "name")
    FileDto fileEntityToDTO(FileEntity entity);

}
