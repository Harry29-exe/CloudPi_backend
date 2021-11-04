package com.cloudpi.cloudpi_backend.configuration.network;

import lombok.Data;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

import java.util.List;


@Data
public class LocalNetworksInfo {
    private List<IpAddressMatcher> networks;

    public LocalNetworksInfo(List<String> networks) {
        this.networks = networks.stream()
                .map(IpAddressMatcher::new)
                .toList();
    }

    public boolean isAddressLocal(String address) {
        for (var network : networks) {
            if (network.matches(address)) {
                return true;
            }
        }

        return false;
    }


}
