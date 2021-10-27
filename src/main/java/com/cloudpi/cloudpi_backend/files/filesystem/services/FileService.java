package com.cloudpi.cloudpi_backend.files.filesystem.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    void saveFile(String path, MultipartFile file) throws IOException;

}
