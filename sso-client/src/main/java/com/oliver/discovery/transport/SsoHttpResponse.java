package com.oliver.discovery.transport;

import lombok.Data;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

/**
 * com.oliver.discovery.transport SsoHttpResponse
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/12 20:33
 */
@Data
public class SsoHttpResponse<T> {
    private final int statusCode;
    private final T entity;
    private final Map<String, String> headers;
    private final URI location;

    public Map<String, String> getHeaders() {
        return headers == null ? Collections.<String, String>emptyMap() : headers;
    }
}
