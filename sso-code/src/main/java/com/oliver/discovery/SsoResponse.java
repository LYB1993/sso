package com.oliver.discovery;

import lombok.Data;

import java.net.URI;
import java.util.Map;

/**
 * com.oliver.discovery SsoResponse
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/24 18:42
 */
@Data
public class SsoResponse<T> {
    private final int statusCode;
    private final T entity;
    private final Map<String, String> headers;
    private final URI location;
}
