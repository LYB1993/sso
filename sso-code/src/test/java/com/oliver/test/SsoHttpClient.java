package com.oliver.test;

import com.oliver.appinfo.InstanceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * com.oliver.test SsoHttpClient
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/26 16:25
 */
public interface SsoHttpClient {

    HttpResponse<Void> register(String appName);

    HttpResponse<Void> cancel(String id);

    HttpResponse<Void> sendHeartBeat(String id, InstanceInfo info, InstanceInfo.InstanceStatus status);
}
