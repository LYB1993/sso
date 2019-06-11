package com.oliver.configure.intercept;

import com.oliver.bean.eo.Constant;
import com.oliver.bean.vo.Param;
import com.oliver.configure.SsoProperties;
import com.oliver.configure.SsoServerProperties;
import com.oliver.service.IRedisService;
import com.oliver.util.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * com.oliver.configure.intercept SsoIntercept
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/4 18:55
 */
@Component
public class SsoIntercept extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SsoIntercept.class);


    @Resource
    private IRedisService redisService;

    @Resource
    private SsoProperties ssoProperties;

    @Resource
    private SsoServerProperties ssoServerProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String ssoUrl = ssoProperties.getSsoUrl();
        if (StringUtils.isBlank(ssoUrl) && !ssoServerProperties.isSsoServer()) {
            LOGGER.error("don't resolve configuration spring.sso.sso-url; SsoUrl is required");
            throw new RuntimeException("don't resolve configuration spring.sso.sso-url;SsoUrl is required");
        }
        String uri = request.getRequestURI();
        if (LOGGER.isDebugEnabled()) {
            String contextPath = request.getContextPath();
            LOGGER.debug("preHandle contextPath:{},preHandle uri:{}", contextPath, uri);
        }
        //用户登出
        redirectLogout(response, ssoUrl, uri);
        //验证cookie中的信息
        if (validatedCookie(request, response)) {
            return true;
        }
        StringBuffer url = request.getRequestURL();
        String redirectUrl = generateRedirectUrl(request, ssoUrl, url);
        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            LOGGER.error("page jump is failure,Exception:{}", e.getMessage());
            if (LOGGER.isDebugEnabled()) {
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * 确认cookie中的用户信息
     *
     * @param request  request
     * @param response response
     * @return 是否已经登陆
     */
    private boolean validatedCookie(HttpServletRequest request, HttpServletResponse response) {
        String cookieValue = CookieUtils.getValueByCookies(request.getCookies());
        if (StringUtils.isBlank(cookieValue)) {
            cookieValue = request.getParameter(Constant.COOKIE_NAME.getValue());
            if (StringUtils.isBlank(cookieValue)) {
                return false;
            }
        }
        if (redisService.get(new Param<>(cookieValue)) != null) {
            CookieUtils.addCookie(response, cookieValue);
            return true;
        }
        return false;
    }

    /**
     * 用户登出
     *
     * @param response response
     * @param ssoUrl   ssoUrl
     * @param uri      uri
     */
    private void redirectLogout(HttpServletResponse response, String ssoUrl, String uri) {
        if (StringUtils.equals(uri, Constant.SEPARATOR_URL.getValue()
                .concat(Constant.PAGE_LOGOUT.getValue()))) {
            String redirectUrl = ssoUrl
                    .concat(Constant.SEPARATOR_URL.getValue())
                    .concat(Constant.PAGE_LOGOUT.getValue());
            try {
                response.sendRedirect(redirectUrl);
            } catch (IOException e) {
                LOGGER.error("logout page jump is failure,Exception:{}", e.getMessage());
            }
        }
    }


    /**
     * 生成重定向url地址
     * 如果sso地址为空，则此时为sso服务端访问，
     * 直接定向到登陆页面
     *
     * @param request request
     * @param ssoUrl  单点地址
     * @param url     请求地址
     * @return 重定向url地址
     */
    private String generateRedirectUrl(HttpServletRequest request, String ssoUrl, StringBuffer url) {
        String redirectUrl;
        if (StringUtils.isBlank(ssoUrl)) {
            redirectUrl = request.getScheme()
                    .concat("://")
                    .concat(request.getServerName())
                    .concat(":" + request.getServerPort())
                    .concat(request.getContextPath())
                    .concat(Constant.SEPARATOR_URL.getValue())
                    .concat(Constant.PAGE_LOGIN.getValue());
        } else {
            redirectUrl = ssoUrl
                    .concat(Constant.SEPARATOR_URL.getValue())
                    .concat(Constant.PAGE_LOGIN.getValue())
                    .concat("?")
                    .concat(Constant.REDIRECT_URL.getValue())
                    .concat("=")
                    .concat(url.toString());
        }
        return redirectUrl;
    }
}
