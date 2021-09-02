package com.cloudpi.cloudpi_backend.files_info.mapping;

import com.cloudpi.cloudpi_backend.configuration.model_mapper.ModelMapperConfig;
import com.cloudpi.cloudpi_backend.files_info.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files_info.dto.FileDto;
import com.cloudpi.cloudpi_backend.files_info.dto.FileSystemDto;
import com.cloudpi.cloudpi_backend.files_info.entities.RootDirectoryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

public class FilesMappingTest {
    private ModelMapperConfig modelMapperBean = new ModelMapperConfig();
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapper();
        modelMapperBean.applyMappingsFromPackage(modelMapper, "com.cloudpi.cloudpi_backend.files_info");
    }

    @Test
    public void should_map_DirectoryEntity_to_DirectoryDto() {
//        val directoryEntity = FileDto(1L, 0L, "dir", "/", Date(), Date(), Date(), 0L)
        modelMapper.createTypeMap(RootDirectoryEntity.class, FileSystemDto.class);
        modelMapper.validate();
        modelMapper.createTypeMap(FileDto.class, DirectoryDto.class);
        modelMapper.validate();
    }

//    @Test
//    fun `should map RootDirectoryEntity to Filesystem dto`() {
//        modelMapper.createTypeMap(RootDirectoryEntity::class.java, kFileSystemDto::class.java)
//        modelMapper.validate()
//    }
}
