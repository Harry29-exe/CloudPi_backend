package com.cloudpi.cloudpi_backend.utils;

import org.modelmapper.ModelMapper;

public class MappingUtils {
    public static void testMapping(ModelMapper mapper, Class<?> src, Class<?> dest) {
        if(mapper.getTypeMap(src, dest) == null) {
            mapper.createTypeMap(src, dest);
        }
        mapper.validate();
    }
}
