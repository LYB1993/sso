package com.oliver.service.impl;

import com.oliver.bean.User;
import com.oliver.bean.vo.Result;
import com.oliver.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * com.oliver.service.impl UserServiceImpl
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/5/30 16:43
 */
@Service
public class UserServiceImpl implements IUserService {
    @Override
    public Result<User> validateUser(String userName, String passWord) {
        if (StringUtils.equals("admin", userName) && StringUtils.equals("123", passWord)) {
            User user = new User();
            user.setUserName("admin");
            user.setPassWord("123");
            return new Result<User>().success(user);
        }
        return new Result<User>().error("用户验证失败！");
    }
}
