package com.oliver.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * com.oliver.com.oliver.bean SsoObject
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/5/28 15:26
 */
@Getter
@Setter
@ToString
public class SsoObject implements Serializable {
    private static final long serialVersionUID = 2398589790287820839L;
    private String userName;
    private String passWord;
    private String version;
    private Long loginTime;
    private long expireTime = 30 * 60;
}
