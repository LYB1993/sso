package com.oliver.test;

import com.oliver.appinfo.InstanceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * com.oliver.test RealHttpClient
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/26 17:40
 */
public class RealHttpClient implements SsoHttpClient {
    private static Logger log = LoggerFactory.getLogger(RealHttpClient.class);

    @Override
    public HttpResponse<Void> register(String appName) {
        log.info("application is register:{}", appName);
        return null;
    }

    @Override
    public HttpResponse<Void> cancel(String id) {
        log.info("application is cancel:{}", id);
        return null;
    }

    @Override
    public HttpResponse<Void> sendHeartBeat(String id, InstanceInfo info, InstanceInfo.InstanceStatus status) {
        return null;
    }
}
