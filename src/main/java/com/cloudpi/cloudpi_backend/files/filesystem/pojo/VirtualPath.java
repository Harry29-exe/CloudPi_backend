package com.cloudpi.cloudpi_backend.files.filesystem.pojo;

import com.cloudpi.cloudpi_backend.exepctions.files.InvalidPathException;
import com.google.common.collect.ImmutableList;
import lombok.Getter;

import java.util.List;


public class VirtualPath {
    @Getter
    private final String username;
    private ImmutableList<String> directories = null;
    @Getter
    private final String parentDirectoryPath;
    @Getter
    private final String fileName;

    public VirtualPath(String path) {
        int index = path.indexOf(":");
        if(index < 0) {
            throw InvalidPathException.invalidPathFormat();
        }
        username = path.substring(0, index);
        parentDirectoryPath = path.substring(index+1);
        var fileNameIndex = parentDirectoryPath.lastIndexOf('/') + 1;
        fileName = parentDirectoryPath.substring(fileNameIndex);
    }

    public ImmutableList<String> getDirectoriesInPath() {
        return directories == null?
                this.pathToDirectories() :
                directories;
    }

    private ImmutableList<String> pathToDirectories() {
        var pathParts = List.of(parentDirectoryPath.split("/"));
        directories = (ImmutableList<String>) pathParts.subList(0, pathParts.size() - 1);
        return directories;
    }
}
