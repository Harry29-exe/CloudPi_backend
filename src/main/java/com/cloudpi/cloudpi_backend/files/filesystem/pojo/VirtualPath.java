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
    private final String path;
    @Getter
    private final String entityName;

    public VirtualPath(String path) {
        int incorrectIndex = path.indexOf("//");
        if(incorrectIndex >= 0) {
            //TODO change exception
            throw new IllegalArgumentException("Incorrect path");
        }
        this.path = path;
        int fileNameIndex = path.lastIndexOf('/');
        username = path.substring(0, fileNameIndex);
        var lastSlashIndex = path.lastIndexOf('/');
        parentDirectoryPath = path.substring(0, lastSlashIndex);
        entityName = path.substring(lastSlashIndex+1);
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
