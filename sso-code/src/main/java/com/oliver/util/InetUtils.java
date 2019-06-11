package com.oliver.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

/**
 * com.oliver.util InetUtils
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/10 11:14
 */
public class InetUtils {

    private static final Logger log = LoggerFactory.getLogger(InetUtils.class);


    /**
     * 获取客户端的ip信息
     *
     * @return IP信息
     */
    public static InetAddress getClientAddress() {
        InetAddress result = null;
        int lowest = Integer.MAX_VALUE;
        try {
            for (Enumeration<NetworkInterface> nics = NetworkInterface
                    .getNetworkInterfaces(); nics.hasMoreElements(); ) {
                NetworkInterface ifc = nics.nextElement();
                if (ifc.isUp()) {
                    log.trace("Testing interface:{} ", ifc.getDisplayName());
                    if (ifc.getIndex() < lowest || result == null) {
                        lowest = ifc.getIndex();
                    } else {
                        continue;
                    }
                    for (Enumeration<InetAddress> addrs = ifc
                            .getInetAddresses(); addrs.hasMoreElements(); ) {
                        InetAddress address = addrs.nextElement();
                        if (address instanceof Inet4Address && !address.isLoopbackAddress()) {
                            log.trace("Found non-loopback interface:{} "
                                    , ifc.getDisplayName());
                            result = address;
                        }
                    }
                }
            }
        } catch (SocketException e) {
            log.error("Cannot get first non-loopback address", e);
        }
        if (result != null) {
            return result;
        }
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            log.warn("Unable to retrieve localhost");
        }
        return null;
    }

    /**
     * 验证地址状态
     *
     * @param host host
     * @param port port
     * @return 是否可用
     */
    public static boolean isHostConnectAble(String host, int port) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port));
        } catch (IOException e) {
            log.warn("Address unavailable.Address:{}:{}", host, port);
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                log.error("socket close failure.{}", e.getMessage());
            }
        }
        return true;
    }
}
