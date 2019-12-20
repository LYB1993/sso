package com.oliver.test;

import com.oliver.appinfo.InstanceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * com.oliver.test SsoHttpClientDecorator
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/26 16:28
 */
public abstract class SsoHttpClientDecorator implements SsoHttpClient {
    private static Logger log = LoggerFactory.getLogger(SsoHttpClientDecorator.class);

    public enum Type {
        register,
        cancel,
        sendHeartBeat
    }

    public interface RequestExecutor<R> {
        HttpResponse<R> execute(SsoHttpClient client);

        Type getType();
    }

    abstract <R> HttpResponse<R> execute(RequestExecutor<R> executor);

    @Override
    public HttpResponse<Void> register(String appName) {
        log.debug("register appName:{}", appName);
        return execute(new RequestExecutor<Void>() {
            @Override
            public HttpResponse<Void> execute(SsoHttpClient client) {
                return client.register(appName);
            }

            @Override
            public Type getType() {
                return Type.register;
            }
        });
    }

    @Override
    public HttpResponse<Void> cancel(String id) {
        log.debug("cancel id:{}", id);
        return execute(new RequestExecutor<Void>() {
            @Override
            public HttpResponse<Void> execute(SsoHttpClient client) {
                return client.cancel(id);
            }

            @Override
            public Type getType() {
                return Type.cancel;
            }
        });
    }

    @Override
    public HttpResponse<Void> sendHeartBeat(String id, InstanceInfo info, InstanceInfo.InstanceStatus status) {
        return execute(new RequestExecutor<Void>() {
            @Override
            public HttpResponse<Void> execute(SsoHttpClient client) {
                return client.sendHeartBeat(id, info, status);
            }

            @Override
            public Type getType() {
                return Type.sendHeartBeat;
            }
        });
    }
}
