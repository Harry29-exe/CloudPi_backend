package com.cloudpi.cloudpi_backend.files_info.dto

import java.util.*

data class FileIdDto(
    val fileId: Long,
    val fileName: String,
    var lastModified: Date
)