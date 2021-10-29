package com.cloudpi.cloudpi_backend.files.filesystem.dto;

import lombok.Getter;

@Getter
public class VirtualPath {
    private final String username;
    private final String path;

    public VirtualPath(String path) {
        int index = path.indexOf(":");
        username = path.substring(0, index);
        this.path = path.substring(index+1);
    }
}
