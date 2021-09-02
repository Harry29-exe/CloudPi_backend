package com.cloudpi.cloudpi_backend.configuration.network;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class NetworkConfig {

    @Bean
    public NetworkInfo createNetworkInfo() throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
//        inetAddress.
//        System.out.println("\n\n"+inetAddress.getHostAddress()+"\n\n");
        return new NetworkInfo();
    }
}
