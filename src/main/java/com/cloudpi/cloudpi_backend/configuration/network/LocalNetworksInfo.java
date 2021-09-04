package com.cloudpi.cloudpi_backend.configuration.network;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import static com.cloudpi.cloudpi_backend.configuration.network.NetworkAddressUtils.convertFromBytesToInt;

@Data
@AllArgsConstructor
public class LocalNetworksInfo {
    private List<LocalNetwork> networks;


    public boolean isAddressLocal(byte[] address) {
        return this.isAddressLocal(convertFromBytesToInt(address));
    }

    public boolean isAddressLocal(int address) {
        for(var network : networks) {
            if(network.isAddressFromNetwork(address)) {
                return true;
            }
        }

        return false;
    }


}
