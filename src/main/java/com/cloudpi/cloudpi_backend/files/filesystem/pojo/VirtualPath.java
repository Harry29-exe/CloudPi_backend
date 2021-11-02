package com.cloudpi.cloudpi_backend.files.filesystem.pojo;

import com.cloudpi.cloudpi_backend.exepctions.files.InvalidPathException;
import com.google.common.collect.ImmutableList;
import lombok.Getter;

import java.util.List;

@Getter
public class VirtualPath {
    private final String username;
    private final ImmutableList<String> directories;
    private final String fileName;

    public VirtualPath(String path) {
        int index = path.indexOf(":");
        if(index < 0) {
            throw InvalidPathException.invalidPathFormat();
        }
        username = path.substring(0, index);
        var pathParts = List.of(path.substring(index+1).split("/"));
        directories = (ImmutableList<String>) pathParts.subList(0, pathParts.size() - 1);
        fileName = pathParts.get(pathParts.size() - 1);
    }
}
