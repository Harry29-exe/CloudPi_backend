package com.cloudpi.cloudpi_backend.configuration.network;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

import static com.cloudpi.cloudpi_backend.configuration.network.NetworkAddressUtils.convertFromBytesToInt;

@Data
public class LocalNetworksInfo {
    private List<LocalNetwork> networks;

    public LocalNetworksInfo(List<LocalNetwork> networks) {
        this.networks = networks;
    }

    public boolean isAddressLocal(String address) {
        var integers = (Integer[]) Arrays.stream(address.split("\\.")).map(Integer::parseInt).toArray();
        int rawAddress = NetworkAddressUtils.convertFromBytesToInt(integers);
        return this.isAddressLocal(rawAddress);
    }

    public boolean isAddressLocal(byte[] address) {
        return this.isAddressLocal(convertFromBytesToInt(address));
    }

    public boolean isAddressLocal(int address) {
        for (var network : networks) {
            if (network.isAddressFromNetwork(address)) {
                return true;
            }
        }

        return false;
    }


}
