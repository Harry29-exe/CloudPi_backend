package com.cloudpi.cloudpi_backend.files_info.dto

//@Dto
class kFileSystemDto(
    val id: kFsObjectIdDto,
    val childrenDirectories: List<kFsDirectoryDto>,
    val childrenFiles: List<kFsObjectIdDto>,
    val rootPath: String
)

