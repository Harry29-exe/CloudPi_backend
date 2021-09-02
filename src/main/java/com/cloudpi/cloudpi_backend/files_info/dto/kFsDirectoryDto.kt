package com.cloudpi.cloudpi_backend.files_info.dto

import com.cloudpi.cloudpi_backend.configuration.Dto

@Dto
data class kFsDirectoryDto (
    val id: kFsObjectIdDto,
    val childrenDirectories: List<kFsDirectoryDto>,
    val childrenFiles: List<kFsObjectIdDto>
)