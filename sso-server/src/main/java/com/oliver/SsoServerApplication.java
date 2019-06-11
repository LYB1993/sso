package com.oliver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * com.oliver SsoServerApplication
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/5/28 14:27
 */
@SpringBootApplication
public class SsoServerApplication {
    private final static Logger LOGGER = LoggerFactory.getLogger(SsoServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SsoServerApplication.class, args);
        LOGGER.info("SSO Server have already started.");
    }
}
