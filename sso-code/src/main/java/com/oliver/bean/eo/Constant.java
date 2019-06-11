package com.oliver.bean.eo;

/**
 * com.oliver.com.oliver.bean.eo Constant
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/3 17:49
 */
public enum Constant {
    /**
     * cookie name
     */
    COOKIE_NAME("sso_sessionid"),

    /**
     * cookie validate
     */
    COOKIE_VALIDATE("sso_validate"),

    /**
     * redirect_url
     */
    REDIRECT_URL("redirect_url"),

    PAGE_LOGIN("login"),

    PAGE_LOGOUT("logout"),

    PAGE_INDEX("index"),

    PAGE_ERROR("error"),

    KEY_CLIENTS("sso_clients"),

    THREAD_PREFIX("spring.sso"),

    /**
     * URL separator
     */
    SEPARATOR_URL("/");

    private String value;

    Constant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
