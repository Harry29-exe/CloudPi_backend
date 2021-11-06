package com.cloudpi.cloudpi_backend.files.filesystem.dto.mappers;

import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.DirectoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DirectoryMapper {

    DirectoryMapper INSTANCE = Mappers.getMapper(DirectoryMapper.class);

    default DirectoryDto directoryEntityToDto(DirectoryEntity entity) {
        return new DirectoryDto(
                entity.getId(),
                entity.getParent().getId(),
                entity.getName(),
                entity.getPath(),
                entity.getChildrenSize(),
                entity.getCreatedAt(),
                entity.getLastChildrenModification()
        );
    };


}
