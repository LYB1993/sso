package com.oliver.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * com.oliver.test RealeHttpClient
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/26 17:40
 */
public class RealeHttpClient implements SsoHttpClient {
    private static Logger log = LoggerFactory.getLogger(RealeHttpClient.class);

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
}
