package com.cloudpi.cloudpi_backend.files.filesystem.services;

public interface VFilesystemService {

    void createVirtualFilesystem(Long userId, Long driveSize);

    void createVirtualFilesystem(Long userId);

}
