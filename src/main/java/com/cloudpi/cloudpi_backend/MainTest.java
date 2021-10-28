package com.cloudpi.cloudpi_backend;

import com.cloudpi.cloudpi_backend.configuration.network.LocalNetworksInfo;
import com.cloudpi.cloudpi_backend.configuration.network.Network;
import com.cloudpi.cloudpi_backend.configuration.network.NetworkConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MainTest {

    public static void main(String[] args) throws IOException {
        File[] paths;
        FileSystemView fsv = FileSystemView.getFileSystemView();
        for (var fs : FileSystems.getDefault().getFileStores()) {
            if(fs.name().startsWith("/dev/") && !fs.name().startsWith("/dev/loop")) {
                System.out.println(fs.name());
                System.out.println(fs.type());
//                System.out.println(fs.getBlockSize());
                System.out.println( (fs.getTotalSpace() / Math.pow(10, 9))  + "\n\n");
            }
        }

        // returns pathnames for files and directory
//        paths = File.listRoots();
//
//        // for each pathname in pathname array
//        for(File path:paths) {
//            // prints file and directory paths
//            System.out.println("Drive Name: "+path);
//            System.out.println("Description: "+fsv.getSystemTypeDescription(path));
//        }
    }
}
