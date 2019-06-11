package com.oliver.controller;

import com.oliver.bean.SsoObject;
import com.oliver.bean.User;
import com.oliver.bean.eo.Constant;
import com.oliver.bean.eo.HttpCode;
import com.oliver.bean.vo.Param;
import com.oliver.bean.vo.Result;
import com.oliver.configure.SsoServerProperties;
import com.oliver.service.IRedisService;
import com.oliver.service.IUserService;
import com.oliver.util.CookieUtils;
import com.oliver.util.SsoUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * com.oliver.controller SsoAuthenticationController
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/5/28 15:23
 */
@Controller
@RequestMapping
public class SsoAuthenticationController {
    private final static Logger LOGGER = LoggerFactory.getLogger(SsoAuthenticationController.class);

    private final static String REDIS_OK = "OK";


    @Resource
    private IUserService userService;

    @Resource
    private IRedisService redisService;

    @Resource
    private SsoServerProperties serverProperties;


    @GetMapping("login")
    public String login(Model model, HttpServletRequest request) {
        model.addAttribute(Constant.REDIRECT_URL.getValue(),
                request.getParameter(Constant.REDIRECT_URL.getValue()));
        return Constant.PAGE_LOGIN.getValue();
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request) {
        String cookieValue = CookieUtils.getValueByCookies(request.getCookies());
        if (StringUtils.isNotBlank(cookieValue)) {
            redisService.remove(cookieValue);
        }
        return Constant.PAGE_LOGIN.getValue();
    }


    @RequestMapping("toindex")
    public String toIndex(HttpServletRequest request,
                          HttpServletResponse response,
                          String loginName, String passWord, String remember) {
        SsoObject ssoObject;
        String sessionId;
        //1.获取cookie信息,并进行判断，成功直接跳转，否则判断用户
        ssoObject = getSsoObject(request);
        String redirectUrl = request.getParameter(Constant.REDIRECT_URL.getValue());
        if (StringUtils.isBlank(redirectUrl)) {
            redirectUrl = Constant.PAGE_INDEX.getValue();
        }
        if (ssoObject != null) {
            sessionId = SsoUtils.generateSessionId(ssoObject);
            redirectUrl = SsoUtils.enhanceRedirectUrl(redirectUrl, sessionId);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Get user by cookie login info" +
                        " sessionId is :{},redirectUrl:{}", sessionId, redirectUrl);
            }
            return new Result<>(redirectUrl, true).getPageName();
        }
        //2.验证用户信息
        Result<User> userResult = userService.validateUser(loginName, passWord);
        if (userResult.getCode() != HttpCode.HTTP_OK.getCode()) {
            return new Result(HttpCode.HTTP_ACCEPTED, true)
                    .getPageName();
        }
        //3.生成sessionId
        ssoObject = SsoUtils.buildSsoUser(userResult);
        sessionId = SsoUtils.generateSessionId(ssoObject);
        //4.添加到redis 和 cookie
        CookieUtils.addCookie(response, sessionId, StringUtils.isNotBlank(remember)
                , serverProperties.getCookieLeaseTime());
        String setex = redisService.setex(sessionId, ssoObject);
        if (StringUtils.equals(setex, REDIS_OK)) {
            redirectUrl = SsoUtils.enhanceRedirectUrl(redirectUrl, sessionId);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("validate user login info" +
                        " sessionId is :{},redirectUrl:{}", sessionId, redirectUrl);
            }
            return new Result(redirectUrl, true).getPageName();
        }
        return new Result(Constant.PAGE_ERROR.getValue(), true).getPageName();
    }


    @GetMapping("index")
    public String index(HttpServletRequest request) {
        SsoObject cookieSsoObject = getSsoObject(request);
        if (cookieSsoObject == null) {
            return new Result<>(HttpCode.HTTP_ACCEPTED).getPageName();
        }
        return Constant.PAGE_INDEX.getValue();
    }


    /**
     * 通过cookie获取redis中的ssoObject对象
     *
     * @param request request
     * @return sso Object
     */
    private SsoObject getSsoObject(HttpServletRequest request) {
        String value = CookieUtils.getValueByCookies(request.getCookies());
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("cookie:{}", value);
        }
        return redisService.get(new Param<>(value));
    }

}
