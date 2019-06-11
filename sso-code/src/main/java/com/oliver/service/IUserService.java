package com.oliver.service;

import com.oliver.bean.User;
import com.oliver.bean.vo.Result;

/**
 * com.oliver.service IUserService
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/5/30 14:25
 */
public interface IUserService {
    /**
     * 验证用户信息
     *
     * @param userName 用户登陆名
     * @param passWord 用户密码
     * @return 数据
     */
    Result<User> validateUser(String userName, String passWord);
}
