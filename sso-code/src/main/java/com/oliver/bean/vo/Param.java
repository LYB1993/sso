package com.oliver.bean.vo;

import lombok.Data;

/**
 * com.oliver.com.oliver.bean.vo Param
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/3 20:44
 */
@Data
public class Param<T> {
    public Param(String key) {
        this.key = key;
    }

    private String key;
}
