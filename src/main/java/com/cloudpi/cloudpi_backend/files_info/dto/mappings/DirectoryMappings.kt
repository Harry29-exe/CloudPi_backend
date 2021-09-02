package com.cloudpi.cloudpi_backend.files_info.dto.mappings

//class DirectoryMappings : MapFunction {
//
//    override fun addMappingToModelMapper(modelMapper: ModelMapper) {
//        modelMapper.typeMap(DirectoryEntity::class.java, DirectoryDto::class.java)
//            .addMappings { mapper ->
//                mapper.map(
//                    { src ->
//                        var size = 0L
//                        src.children.forEach { child -> size += child.size }
//                        size
//                    },
//                    { dest: DirectoryDto, value: Long ->
//                        dest.childrenSize = value
//                    }
//                )
//                mapper.map(
//                    { src -> src.children.filter { file: FileSystemObjectEntity -> file. }},
//                    { dest: DirectoryDto, value:  ->
//
//                    }
//                )
//            }
//    }
//}