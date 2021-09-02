package com.cloudpi.cloudpi_backend.files_info.dto

import java.util.*

data class FileDto(
    val id: Long,
    var parentId: Long,
    var fileName: String,
    var path: String,
    var createdAt: Date,
    var lastModified: Date,
    var lastRead: Date,
    var size: Long
)