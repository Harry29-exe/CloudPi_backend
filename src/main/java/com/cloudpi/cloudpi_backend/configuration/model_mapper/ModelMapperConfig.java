package com.cloudpi.cloudpi_backend.configuration.model_mapper;

import org.modelmapper.ModelMapper;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ModelMapperConfig {
    private final Logger logger;

    public ModelMapperConfig() {
        logger = LoggerFactory.getLogger("ModelMapper.Logger");
    }

    @Bean
    public ModelMapper getModelMapper() {
        var modelMapper = new ModelMapper();

        applyMappingsFromPackage(modelMapper, "com.cloudpi.cloudpi_backend");
        return modelMapper;
    }

    public void applyMappingsFromPackage(ModelMapper modelMapper, String packageName) {
        var reflections = new Reflections(packageName);
        var results = reflections.getSubTypesOf(MapFunction.class);

        for (var result : results) {
            MapFunction instance = null;

            for (var constructor : result.getConstructors()) {
                if (constructor.getParameterCount() == 0) {
                    Object temp = null;
                    try {
                        temp = constructor.newInstance();
                    } catch (Exception ex) {
                        logger.error("Something went very wrong while configuring ModelMapper");
                    }

                    if (temp instanceof MapFunction) {
                        instance = (MapFunction) temp;
                        break;
                    }
                }
            }

            if (instance == null) {
                logger.error("MapFunction has to have no args constructor");
            } else {
                instance.addMappingToModelMapper(modelMapper);
            }
            System.out.println(result);
        }
    }
}
