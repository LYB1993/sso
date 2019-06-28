package com.oliver.test;

import lombok.Data;

import java.util.Map;

/**
 * com.oliver.test HttpResponse
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/26 16:39
 */
@Data
public class HttpResponse<T> {
    private int statusCode;
    private T entity;
    private Map<String, String> headers;
}
