package com.oliver.util;

import com.oliver.bean.eo.Constant;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * com.oliver.util CookieUtils
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/3 17:27
 */
public final class CookieUtils {
    private CookieUtils() {

    }


    /**
     * 根据cookie name 获取值
     *
     * @param cookies cookie数据
     * @return cookie value
     */
    public static String getValueByCookies(Cookie[] cookies) {
        return getValueByCookies(cookies, Constant.COOKIE_NAME);
    }

    /**
     * 根据cookie name 获取值
     *
     * @param cookies cookie数据
     * @param name    cookie Name
     * @return cookie value
     */
    public static String getValueByCookies(Cookie[] cookies, Constant name) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(cookie.getName(), name.getValue())) {
                    return cookie.getValue();
                }
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * add cookie
     *
     * @param response   response
     * @param value      cookie value
     * @param remember   remember password
     * @param expireTime expire time
     */
    public static void addCookie(HttpServletResponse response, String value, boolean remember, Long expireTime) {
        Cookie cookie = new Cookie(Constant.COOKIE_NAME.getValue(), value);
        cookie.setMaxAge(remember ? expireTime.intValue() : -1);
        cookie.setPath(Constant.SEPARATOR_URL.getValue());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }


    /**
     * add cookie
     * default remember false
     *
     * @param response response
     * @param value    cookie value
     */
    public static void addCookie(HttpServletResponse response, String value) {
        addCookie(response, value, false, -1L);
    }

}
