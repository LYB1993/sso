package com.oliver.bean.vo;

import lombok.Data;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

/**
 * com.oliver.bean.vo HostInfo
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/10 16:03
 */
@Data
public class HostInfo {
    private String ipAddress;
    private int port;
    private String hostname;
    private boolean usAble;

    public int getIpAddressAsInt() {
        InetAddress inetAddress;
        String host = this.ipAddress;
        if (host == null) {
            host = this.hostname;
        }
        try {
            inetAddress = InetAddress.getByName(host);
        } catch (final UnknownHostException e) {
            throw new IllegalArgumentException(e);
        }
        return ByteBuffer.wrap(inetAddress.getAddress()).getInt();
    }
}
