package com.cloudpi.cloudpi_backend.configuration.network;

import org.springframework.lang.Nullable;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class NetworkAddressUtils {

    public static int convertFromBytesToInt(byte[] address) {
        int intAddress = 0;
        for (int i = 0; i < 3; i++) {
            intAddress += Byte.toUnsignedInt(address[i]);
            intAddress = intAddress << 8;
        }
        intAddress += address[3];
        return intAddress;
    }

    public static byte[] convertFromIntToBytes(int address) {
        byte[] byteAddress = new byte[4];
        for (int i = 0; i < 4; i++) {
            byteAddress[i] = (byte) ((address << i) >> 24);
        }

        return byteAddress;
    }

    public static int convertFromBytesToInt(Integer[] address) {
        int intAddress = 0;
        for (int i = 0; i < 3; i++) {
            intAddress += address[i];
            intAddress = intAddress << 8;
        }
        intAddress += address[3];
        return intAddress;
    }

    public static List<LocalNetwork> scanForLocalNetworks() {

        List<InterfaceAddress> networkAddresses = new ArrayList<>();
        try {
            NetworkInterface.networkInterfaces()
                    .map(NetworkInterface::getInterfaceAddresses)
                    .forEach(networkAddresses::addAll);

            return networkAddresses.stream()
                    .map(NetworkAddressUtils::checkIfAddressIsLocalAndConvert)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static Integer convertMaskLengthToMask(int maskLength) {
        return 0xFFFFFFFF << (32 - maskLength);
    }

    private static @Nullable
    LocalNetwork checkIfAddressIsLocalAndConvert(InterfaceAddress interfaceAddress) {
        if (interfaceAddress.getAddress().isSiteLocalAddress()) {
            int mask = convertMaskLengthToMask(interfaceAddress.getNetworkPrefixLength());
            int intAddress = convertFromBytesToInt(interfaceAddress.getAddress().getAddress());
            return new LocalNetwork(intAddress & mask, mask);
        }

        return null;
    }


}

//NetworkInterface.networkInterfaces().forEach(networkInterface -> {
//        var interfaceAddresses = networkInterface.getInterfaceAddresses();
//        for(var interfaceAddress: interfaceAddresses) {
//        if(interfaceAddress.getAddress().isSiteLocalAddress()) {
//        int mask = 0xFFFFFFFF << (32 - interfaceAddress.getNetworkPrefixLength());
//        int intAddress = convertFromBytesToInt(interfaceAddress.getAddress().getAddress());
//        networks.add(new LocalNetwork(intAddress & mask, mask));
//        }
//        }
//        });
