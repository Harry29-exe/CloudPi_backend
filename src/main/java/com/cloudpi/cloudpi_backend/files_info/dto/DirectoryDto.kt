package com.cloudpi.cloudpi_backend.files_info.dto

import java.util.*

data class DirectoryDto(
    val id: Long,
    var parentId: Long,
    var fileName: String,
    var path: String,
    var createdAt: Date,
    var lastModified: Date,
    var lastRead: Date,
    var childrenSize: Long,
    val childrenFiles: MutableList<FileDto>,
    val childrenDirectories: MutableList<DirectoryDto>
)