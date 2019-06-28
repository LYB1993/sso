package com.oliver.discovery.transport;

import com.oliver.appinfo.InstanceInfo;

/**
 * com.oliver.discovery.transport SsoHttpClientDecorator
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/12 20:32
 */
public abstract class SsoHttpClientDecorator implements SsoHttpClient {

    public enum RequestType {
        Register,
        Cancel
    }

    public interface RequestExecutor<R> {
        SsoHttpResponse<R> execute(SsoHttpClient delegate);

        RequestType getRequestType();
    }

    protected abstract <R> SsoHttpResponse<R> execute(RequestExecutor<R> requestExecutor);

    @Override
    public SsoHttpResponse<Void> register(InstanceInfo info) {
        return execute(new RequestExecutor<Void>() {
            @Override
            public SsoHttpResponse<Void> execute(SsoHttpClient delegate) {
                return delegate.register(info);
            }

            @Override
            public RequestType getRequestType() {
                return RequestType.Register;
            }
        });
    }

    @Override
    public SsoHttpResponse<Void> sendHeartBeat() {
        return execute(new RequestExecutor<Void>() {
            @Override
            public SsoHttpResponse<Void> execute(SsoHttpClient delegate) {
                return null;
            }

            @Override
            public RequestType getRequestType() {
                return null;
            }
        });
    }
}
