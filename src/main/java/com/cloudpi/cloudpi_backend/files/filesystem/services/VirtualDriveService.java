package com.cloudpi.cloudpi_backend.files.filesystem.services;

public interface VirtualDriveService {

    void createVirtualDriveAndRootDir(Long userId, Long driveSize);

    void createVirtualDriveAndRootDir(Long userId);

}
