package com.oliver.discovery;

import com.oliver.appinfo.InstanceInfo;

/**
 * com.oliver.discovery SsoHttpClient
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/24 18:39
 */
public interface SsoHttpClient {
    /**
     * 服务注册
     * @param instanceInfo 注册的实体基本信息
     * @return 相应信息
     */
    SsoResponse<Void> register(InstanceInfo instanceInfo);

    /**
     * 取消注册信息
     * @param appName 应用名称
     * @param id 应用id
     * @return 响应信息
     */
    SsoResponse<Void> cancel(String appName, String id);

    /**
     * 发送心跳信息
     * @param appName 应用名称
     * @param id 应用id
     * @param info 应用信息
     * @param overriddenStatus 应用状态
     * @return 响应信息
     */
    SsoResponse<InstanceInfo> sendHeartBeat(String appName, String id, InstanceInfo info, InstanceInfo.InstanceStatus overriddenStatus);



}
