package com.cloudpi.cloudpi_backend;

import com.cloudpi.cloudpi_backend.configuration.network.Network;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainTest {

    public static void main(String[] args) throws UnknownHostException {
        var encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123"));
        var address = InetAddress.getByAddress(
                Network.convertFromIntToBytes(Network.convertAddressToInt("192.168.0.84")));
        System.out.println(
                address.isAnyLocalAddress() + "" +
                        address.isSiteLocalAddress() + ""+
                        address.isLinkLocalAddress() +""+
                        address.isMCSiteLocal()  +""+
                        address.isMCNodeLocal()
                );
    }
}
