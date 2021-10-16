package com.cloudpi.cloudpi_backend;

import com.cloudpi.cloudpi_backend.configuration.network.LocalNetworksInfo;
import com.cloudpi.cloudpi_backend.configuration.network.Network;
import com.cloudpi.cloudpi_backend.configuration.network.NetworkConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MainTest {

    public static void main(String[] args) throws UnknownHostException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        var date = Date.valueOf(LocalDate.parse("2020-02-02", formatter));
//        LocalNetworksInfo lanInfo = new LocalNetworksInfo(NetworkConfig.scanForLocalNetworks());
//        lanInfo.getNetworks().forEach(n -> System.out.println(n.toString()));
//        System.out.println(lanInfo.isAddressLocal("::2"));
    }
}
