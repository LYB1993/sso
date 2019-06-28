package com.oliver.discovery.transport;

import com.oliver.appinfo.InstanceInfo;

/**
 * com.oliver.discovery.transport SsoHttpClient
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/12 19:10
 */
public interface SsoHttpClient {
    /**
     * 服务注册
     *
     * @param info 注册的服务的信息
     * @return 返回的数据
     */
    SsoHttpResponse<Void> register(InstanceInfo info);

    /**
     * 发送心跳信息
     *
     * @return 返回的数据
     */
    SsoHttpResponse<Void> sendHeartBeat();

}
