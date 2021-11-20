package com.cloudpi.cloudpi_backend;

import java.util.HashSet;
import java.util.Set;

public class MainTests {

    public static void main(String[] args) {
        String numberToParse = "1_000_000";
        Long number = Long.parseLong(numberToParse.replace("_", ""));
        System.out.println(number);
    }

//    public static void main(String[] args) throws IOException {
//        var homeDir = System.getProperty("user.home");
//        System.out.println(homeDir);
//        Path path = Paths.get(homeDir + "/cloud-pi-test-dir");
//        FileStore fs = Files.getFileStore(path);
//        System.out.println(fs.name());
//        System.out.println(fs.getTotalSpace());
//        System.out.println(fs.getUnallocatedSpace());
//        System.out.println(fs.getUsableSpace());
//    }

}
