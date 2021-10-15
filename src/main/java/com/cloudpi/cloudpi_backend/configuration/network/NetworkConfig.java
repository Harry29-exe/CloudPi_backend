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
            return address.toString().substring(1) + "/" + interfaceAddress.getNetworkPrefixLength();
//            int mask = interfaceAddress.getNetworkPrefixLength();
//            byte[] rawAddress = interfaceAddress.getAddress().getAddress();
//            String addressSeparator = rawAddress.length > 4? "::" : ".";
//
//            StringBuilder builder = new StringBuilder();
//            for(int i = 0; i < rawAddress.length - 1; i++) {
//                builder.append(Byte.toUnsignedInt(rawAddress[i]));
//                builder.append(addressSeparator);
//            }
//            builder.append(rawAddress[rawAddress.length-1]);
//
//            builder.append('/');
//            builder.append(mask);
//            return builder.toString();
        }

        return null;
    }
}
