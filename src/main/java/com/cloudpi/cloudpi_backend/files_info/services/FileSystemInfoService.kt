package com.cloudpi.cloudpi_backend.files_info.services

import com.cloudpi.cloudpi_backend.files_info.dto.DirectoryDto
import com.cloudpi.cloudpi_backend.files_info.dto.FileDto

interface FileSystemInfoService {

    fun getFile(path: String): FileDto

    fun getDirectoryChildren(path: String): Pair<List<DirectoryDto>, List<FileDto>>

    fun createNewFile(path: String, size: Long)

    fun deleteFile(path: String)
}