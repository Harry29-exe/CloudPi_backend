package com.cloudpi.cloudpi_backend.files_info.controllers

import com.cloudpi.cloudpi_backend.files_info.dto.DirectoryDto
import com.cloudpi.cloudpi_backend.files_info.dto.FileDto
import org.springframework.security.core.Authentication

interface FileInfoEndpoints {

    fun getUsersFilesInfo(userAuth: Authentication): DirectoryDto

    fun getFileInfo(userAuth: Authentication, filePath: String): FileDto
}