package com.oliver.configtest;

import com.oliver.configure.SsoProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * com.oliver.configtest ConfigTest
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/5 13:59
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ConfigTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigTest.class);

    @Resource
    private SsoProperties properties;

    @Resource
    private RedisProperties redisProperties;

    @Test
    public void excludePath(){
        LOGGER.debug("excludePath:{}",redisProperties.getHost());
    }
}
