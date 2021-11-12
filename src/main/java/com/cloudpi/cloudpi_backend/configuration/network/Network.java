package com.cloudpi.cloudpi_backend.configuration.network;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Deprecated
public class Network {
    private final int networkAddress;
    private final int mask;

    public Network(String networkAddress, int maskLength) {
        var addressParts = new int[4];
        var addressSections = networkAddress.split("\\.");
        for (int i = 0; i < 4; i++) {

            addressParts[i] = Integer.parseInt(addressSections[i]);
        }

        this.networkAddress = mergeAddressSections(addressParts);
        this.mask = convertMaskLengthToMask(maskLength);
    }

    public boolean isAddressFromNetwork(String address) {
        int rawAddress = convertAddressToInt(address);

        return this.isAddressFromNetwork(rawAddress);
    }

    public boolean isAddressFromNetwork(byte[] address) {
        if (address.length != 4) {
            throw new IllegalArgumentException("Address has to be 4 bytes long");
        }
        int intAddress = mergeAddressSections(address);

        return this.isAddressFromNetwork(intAddress);
    }

    public boolean isAddressFromNetwork(int address) {
        return this.networkAddress == (address & mask);
    }

    public static int convertAddressToInt(String address) {
        var addressParts = new int[4];
        var addressSections = address.split("\\.");
        for (int i = 0; i < 4; i++) {
            addressParts[i] = Integer.parseInt(addressSections[i]);
        }
        return mergeAddressSections(addressParts);
    }

    public static int mergeAddressSections(byte[] address) {
        int intAddress = 0;
        for (int i = 0; i < 3; i++) {
            intAddress += Byte.toUnsignedInt(address[i]);
            intAddress = intAddress << 8;
        }
        intAddress += address[3];
        return intAddress;
    }

    public static int mergeAddressSections(int[] address) {
        int intAddress = 0;
        for (int i = 0; i < 3; i++) {
            intAddress += address[i];
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

    public static Integer convertMaskLengthToMask(int maskLength) {
        return 0xFFFFFFFF << (32 - maskLength);
    }

}

