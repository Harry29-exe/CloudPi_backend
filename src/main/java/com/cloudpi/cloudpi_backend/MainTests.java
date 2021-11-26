package com.cloudpi.cloudpi_backend;

import com.cloudpi.cloudpi_backend.user.entities.UserEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MainTests {

//    public static void main(String[] args) {
//        Paths.get("~/spring-test");
////        Files.createFile()
//    }

    public static void main1(String[] args) throws IOException {
        var homeDir = System.getProperty("user.home");
        System.out.println(homeDir);
        Path path = Paths.get(homeDir + "/cloud-pi-test-dir");
        FileStore fs = Files.getFileStore(path);
        System.out.println(fs.name());
        System.out.println(fs.getTotalSpace());
        System.out.println(fs.getUnallocatedSpace());
        System.out.println(fs.getUsableSpace());
        System.out.println("-----");

        var fileSystem = FileSystems.getDefault();
        var fss = fileSystem.getFileStores();
        fss.forEach(f -> {
            if(f.name().startsWith("/dev/s")) {
                System.out.println(f.getClass());
                System.out.println(f);
                System.out.println(f.name());
                System.out.println(f.type());
            }
        });
    }

    public static void main(String[] args) throws IOException {
        var fileSystem = FileSystems.getDefault();
        var fss = fileSystem.getFileStores();
        fss.forEach(f -> {
            if(f.name().startsWith("/dev/s")) {
                System.out.println(f.getClass());
                System.out.println(f);
                System.out.println(f.name());
                System.out.println(f.type());
            }
        });
    }

}
