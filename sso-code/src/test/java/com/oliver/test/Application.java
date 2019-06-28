package com.oliver.test;

import com.oliver.test.jersey.SsoJerseyClient;
import com.oliver.test.jersey.SsoJerseyClientImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * com.oliver.test Application
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/26 17:46
 */
public class Application {
    private static Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SsoHttpClientDecorator decorator = new SessionHttpClient();
        decorator.register("app");

        SsoJerseyClient client = new SsoJerseyClientImpl();
        log.debug("client:{}", client);
    }
}
