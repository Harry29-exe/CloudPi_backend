package com.cloudpi.cloudpi_backend.files_info.dto

import com.cloudpi.cloudpi_backend.configuration.Dto
import java.util.*

//@Dto
class kFsObjectIdDto(
    val id: Long,
    var path: String,
    var lastModified: Long
) {
    constructor(id: Long, path: String, lastModified: Date)
    : this(id, path, lastModified.time)
}