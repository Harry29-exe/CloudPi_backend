package com.cloudpi.cloudpi_backend.configuration.network;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.cloudpi.cloudpi_backend.configuration.network.NetworkAddressUtils.*;

@Data
@AllArgsConstructor
public class LocalNetwork {
    private Integer address;
    private Integer mask;

    public LocalNetwork(String networkAddress, int maskLength) {
        var addressParts = new Integer[4];
        AtomicInteger i = new AtomicInteger();
        Arrays.stream(networkAddress.split("\\."))
                .forEach(integer ->
                        addressParts[i.getAndIncrement()] = Integer.parseInt(integer)
                );
        this.address = convertFromBytesToInt( addressParts);
        this.mask = convertMaskLengthToMask(maskLength);
    }


    public boolean isAddressFromNetwork(Integer address) {
        return this.address == (address & mask);
    }

    public boolean isAddressFromNetwork(byte[] address) {
        if(address.length != 4) {
            throw new IllegalArgumentException("Address has to ");
        }
        int intAddress = 0;
        for(int i = 0; i < 3; i++ ) {
            intAddress += Byte.toUnsignedInt(address[i]);
            intAddress = intAddress << 8;
        }
        intAddress += address[3];

        return this.isAddressFromNetwork(intAddress);
    }
}

