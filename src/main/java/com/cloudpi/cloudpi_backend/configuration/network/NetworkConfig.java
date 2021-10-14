package com.cloudpi.cloudpi_backend.configuration.network;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
public class NetworkConfig {

    @Bean
    public LocalNetworksInfo createNetworkInfo() {
        return new LocalNetworksInfo(scanForLocalNetworks());
    }

    public static List<String> scanForLocalNetworks() {

        List<InterfaceAddress> networkAddresses = new ArrayList<>();
        try {
            NetworkInterface.networkInterfaces()
                    .map(NetworkInterface::getInterfaceAddresses)
                    .forEach(networkAddresses::addAll);

            return networkAddresses.stream()
                    .map(NetworkConfig::checkIfAddressIsLocalAndConvert)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private static @Nullable
    String checkIfAddressIsLocalAndConvert(InterfaceAddress interfaceAddress) {
        var address = interfaceAddress.getAddress();
        if (address.isSiteLocalAddress() ||
                address.isLoopbackAddress()
        ) {
            int mask = interfaceAddress.getNetworkPrefixLength();
            byte[] rawAddress = interfaceAddress.getAddress().getAddress();

            StringBuilder builder = new StringBuilder();
            for(byte addressSection : rawAddress) {
                builder.append(Byte.toUnsignedInt(addressSection));
            }
            builder.append('/');
            builder.append(mask);
            return builder.toString();
        }

        return null;
    }
}
