package com.oliver.configure;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import javax.annotation.Resource;

/**
 * com.oliver.configure RedisConfigure
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/5/28 17:57
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfigure {

    private final static Logger LOGGER = LoggerFactory.getLogger(RedisConfigure.class);

    @Resource
    private RedisProperties redisProperties;


    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setTestOnBorrow(false);
        JedisPool jedisPool;
        String password = redisProperties.getPassword();
        String host = redisProperties.getHost();
        int port = redisProperties.getPort();
        if (StringUtils.isNotBlank(password)) {
            jedisPool = new JedisPool(jedisPoolConfig, host, port, Protocol.DEFAULT_TIMEOUT, password);
        } else {
            jedisPool = new JedisPool(jedisPoolConfig, host, port, Protocol.DEFAULT_TIMEOUT);
        }
        LOGGER.info("JedisPool Initialization is complete");
        return jedisPool;
    }


}
