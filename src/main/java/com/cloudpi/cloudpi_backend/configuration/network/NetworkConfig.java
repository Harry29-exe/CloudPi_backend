package com.cloudpi.cloudpi_backend.configuration.network;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

@Configuration
public class NetworkConfig {

    @Bean
    public LocalNetworksInfo createNetworkInfo() {
        return new LocalNetworksInfo(NetworkAddressUtils.scanForLocalNetworks());
    }
}
