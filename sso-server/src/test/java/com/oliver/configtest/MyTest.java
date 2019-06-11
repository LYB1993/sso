package com.oliver.configtest;

import org.junit.Test;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * com.oliver.configtest MyTest
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/5 14:42
 */
public class MyTest {

    @Test
    public void test1() throws Exception {
        Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress inetAddress = null;
        while (allNetInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
            if (netInterface.isUp()) {
                Enumeration addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    inetAddress = (InetAddress) addresses.nextElement();
                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                        System.out.println(netInterface.getName());
                        System.out.println(inetAddress.getHostAddress());
                    }
                }
            }
        }
    }

    @Test
    public void test2() throws SocketException {
        InetAddress result = null;
        int lowest = Integer.MAX_VALUE;
        for (Enumeration<NetworkInterface> nics = NetworkInterface
                .getNetworkInterfaces(); nics.hasMoreElements(); ) {
            NetworkInterface ifc = nics.nextElement();
            if (ifc.isUp()) {
                if (ifc.getIndex() < lowest || result == null) {
                    lowest = ifc.getIndex();
                } else {
                    continue;
                }
                for (Enumeration<InetAddress> addrs = ifc
                        .getInetAddresses(); addrs.hasMoreElements(); ) {
                    InetAddress address = addrs.nextElement();
                    if (address instanceof Inet4Address && !address.isLoopbackAddress()) {
                        result = address;
                    }
                }
            }
        }
    }
}
