package com.cloudpi.cloudpi_backend;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainTests {

    public static void main(String[] args) throws IOException {
        var homeDir = System.getProperty("user.home");
        System.out.println(homeDir);
        Path path = Paths.get(homeDir + "/cloud-pi-test-dir");
        FileStore fs = Files.getFileStore(path);
        System.out.println(fs.name());
        System.out.println(fs.getTotalSpace());
        System.out.println(fs.getUnallocatedSpace());
        System.out.println(fs.getUsableSpace());
    }

}