package com.oliver.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * com.oliver.test SessionHttpClient
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/26 17:32
 */
public class SessionHttpClient extends SsoHttpClientDecorator {
    private static Logger log = LoggerFactory.getLogger(SessionHttpClient.class);

    private SsoHttpClient client = new RealHttpClient();

    @Override
    <R> HttpResponse<R> execute(RequestExecutor<R> executor) {
        Type type = executor.getType();
        executor.execute(client);
        log.info("Type:{}", type.name());
        return null;
    }
}
