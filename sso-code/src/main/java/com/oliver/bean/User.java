package com.oliver.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * com.oliver.com.oliver.bean User
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/5/30 14:27
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -7938056483208125833L;
    private String userId;
    private String userName;
    private String passWord;
    private String loginId;
}
