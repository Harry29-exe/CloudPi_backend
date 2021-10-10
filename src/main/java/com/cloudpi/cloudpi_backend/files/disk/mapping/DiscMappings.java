package com.cloudpi.cloudpi_backend.files.disk.mapping;

import com.cloudpi.cloudpi_backend.configuration.model_mapper.MapFunction;
import com.cloudpi.cloudpi_backend.files.disk.dto.DiscDTO;
import com.cloudpi.cloudpi_backend.files.disk.dto.UserDriveDTO;
import com.cloudpi.cloudpi_backend.files.disk.entities.DiscEntity;
import com.cloudpi.cloudpi_backend.files.structure.entities.RootDirectoryEntity;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DiscMappings implements MapFunction {

    @Override
    public void addMappingToModelMapper(ModelMapper modelMapper) {
        mapDiscDTOToEntity(modelMapper);
        mapDiscEntityToDto(modelMapper);
    }

    private void mapDiscEntityToDto(ModelMapper mapper) {
        System.out.println("we1");
        mapper.createTypeMap(DiscEntity.class, DiscDTO.class)
                .addMapping(
                        DiscEntity::getUsersDrives,
                        (dest, value) -> dest.setUserDiscRoots(value == null ?
                                new ArrayList()
                                :
                                ((List<RootDirectoryEntity>) value).stream().map(
                                        rde -> mapper.map(rde, UserDriveDTO.class)
                                ).collect(Collectors.toList()))
                );
    }

    private void mapDiscDTOToEntity(ModelMapper modelMapper) {
        modelMapper.createTypeMap(DiscDTO.class, DiscEntity.class)
                .addMapping(
                        src -> null,
                        (destination, value) -> destination.setUsersDrives(null)
                ).addMapping(
                src -> null,
                (destination, value) -> destination.setId(0)
        );
    }

    private void mapRootDirectoryEntityToUserDriveDTO(ModelMapper modelMapper) {
        modelMapper.createTypeMap(RootDirectoryEntity.class, UserDriveDTO.class)
                .addMapping(
                        src -> src.getOwner().getUsername(),
                        (dest, value) -> dest.setOwnerUsername((String) value)
                ).addMapping(
                RootDirectoryEntity::getChildrenSize,
                (dest, value) -> dest.setUsedCapacity((Long) value)
        );
    }
}
