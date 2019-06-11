package com.oliver.service;

import com.oliver.bean.vo.Param;

import java.util.Set;

/**
 * com.oliver.service IRedisService
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/5/30 18:08
 */
public interface IRedisService {

    /**
     * 设置redis的数据
     *
     * @param sessionId redis key
     * @param object    redis value
     * @return 保存状态
     */
    String setex(final String sessionId, final Object object);

    /**
     * 获取redis的值
     *
     * @param param redis key
     * @param <T>   类型
     * @return redis value
     */
    <T> T get(Param<T> param);

    /**
     * 删除redis的数据
     *
     * @param key key
     * @return 删除状态
     */
    boolean remove(String key);

    /**
     * 添加集合数据
     *
     * @param key    集合key
     * @param values 数据
     * @return 保存状态
     */
    boolean sadd(String key, String... values);

    /**
     * 移除set中特定的数据
     *
     * @param key    set的key
     * @param values 需要移除的值
     * @return 移除状态
     */
    boolean srem(String key, String... values);

    /**
     * 获取set中的数据
     *
     * @param key set的key
     * @return 数据
     */
    Set<String> mget(String key);
}
