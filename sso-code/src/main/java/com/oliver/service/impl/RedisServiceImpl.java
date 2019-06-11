package com.oliver.service.impl;

import com.oliver.bean.vo.Param;
import com.oliver.configure.SsoServerProperties;
import com.oliver.service.IRedisService;
import com.oliver.util.SsoUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Set;

/**
 * com.oliver.util RedisService
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/5/30 17:36
 */
@Service
public class RedisServiceImpl implements IRedisService {

    private final static Logger LOGGER = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Resource(name = "jedisPool")
    private JedisPool jedisPool;

    @Resource
    private SsoServerProperties ssoServerProperties;

    @Override
    public String setex(final String sessionId, final Object object) {
        String setex = null;
        try (Jedis jedis = jedisPool.getResource()) {
            setex = jedis.setex(sessionId.getBytes(StandardCharsets.UTF_8),
                    ssoServerProperties.getRedisLeaseTime().intValue(),
                    SsoUtils.serialize(object));
        } catch (Exception e) {
            LOGGER.error("redis save value is failure,key:{}", sessionId);
        }
        return setex;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Param<T> key) {
        String value = key.getKey();
        if (StringUtils.isNotBlank(value)) {
            try (Jedis jedis = jedisPool.getResource()) {
                byte[] bytes = jedis.get(value.getBytes(StandardCharsets.UTF_8));
                return (T) SsoUtils.deserialization(bytes);
            } catch (Exception e) {
                LOGGER.error("redis get value is failure,key:{}", key);
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean remove(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.del(key.getBytes(StandardCharsets.UTF_8)) != 0;
        } catch (Exception e) {
            LOGGER.error("redis get value is failure,key:{}", key);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean sadd(String key, String... values) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.sadd(key, values) != 0;
        } catch (Exception e) {
            LOGGER.error("redis get value is failure,key:{}", key);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean srem(String key, String... values) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.srem(key, values) != 0;
        } catch (Exception e) {
            LOGGER.error("redis get value is failure,key:{}", key);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Set<String> mget(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.smembers(key);
        } catch (Exception e) {
            LOGGER.error("redis get value is failure,key:{}", key);
            e.printStackTrace();
        }
        return Collections.emptySet();
    }
}
