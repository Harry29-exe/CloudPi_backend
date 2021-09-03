package com.cloudpi.cloudpi_backend;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

public class NetworkTests {

    @Test
    public void network1() throws IOException {
        Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
        while(e.hasMoreElements())
        {
            NetworkInterface n = e.nextElement();
//            System.out.println("\nisVirtual"+n.isVirtual()+"\n");

            Enumeration<InetAddress> ee = n.getInetAddresses();
            while (ee.hasMoreElements())
            {
                System.out.print("\n");
                InetAddress i = (InetAddress) ee.nextElement();
                if(i.isReachable(200) && !i.isLoopbackAddress() && !i.isLinkLocalAddress()) {
                    System.out.println("mulitcast: " + i.isMCNodeLocal());

                    System.out.println(i.getHostAddress());
                    System.out.println(n.getInterfaceAddresses());

                }
                System.out.print("\n");
            }
        }

    }

    @Test
    public void network2() throws IOException {
        List<Integer> addresses = new LinkedList();
        List<Integer> masks = new LinkedList();


        NetworkInterface.networkInterfaces().forEach( networkInterface -> {
                    var interfaceAddresses = networkInterface.getInterfaceAddresses();
                    for(var interfaceAddress: interfaceAddresses) {

//                        if(!interfaceAddress.getAddress().isLoopbackAddress() &&
//                                !interfaceAddress.getAddress().isLinkLocalAddress()) {
//                            masks.add(interfaceAddress.getNetworkPrefixLength())
                        if(interfaceAddress.getAddress().isSiteLocalAddress()) {
                            var address = interfaceAddress.getAddress().getAddress();
                            int mask = 0xFFFFFFFF << (32 - interfaceAddress.getNetworkPrefixLength());
                            int intAddress = 0;
                            for(int i = 0; i < address.length - 1; i++ ) {
                                intAddress += Byte.toUnsignedInt(address[i]);
                                intAddress = intAddress << 8;
                            }
                            intAddress += address[3];
                            masks.add(mask);
                            addresses.add(intAddress & mask);
                        }
                    }
                });

        for(int i = 0; i < addresses.size(); i++) {
            printAddress(addresses.get(i));
            printAddress(masks.get(i));
        }

    }

    @Test
    public void network3() throws UnknownHostException, SocketException {
        InetAddress localHost = Inet4Address.getLocalHost();
        NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);

        for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
            System.out.println("\n-------------");
            printAddresses(address.getAddress().getAddress());
            System.out.println(address.getNetworkPrefixLength());
            System.out.println("-------------\n");
        }
    }


    private void printAddresses(byte[] addresses) {
        for (int i = 0; i < addresses.length; i++) {
            var address = ByteBuffer.allocate(4).putInt(addresses[i]).array();
            for(int j = 0; j < address.length; j++) {
                System.out.print( (address[j] & 0xFF) + (j != 3? "." : "\n"));
            }
        }
    }

    private void printAddress(int addresses) {
        for (int i = 0; i < 4; i++) {
            System.out.print(Byte.toUnsignedInt((byte) ((addresses << i*8) >>> 24)) + (i != 3? ".":"\n"));
        }
    }
}
