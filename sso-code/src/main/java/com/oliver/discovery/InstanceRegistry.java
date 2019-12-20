package com.oliver.discovery;

import com.oliver.appinfo.InstanceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * com.oliver.discovery InstanceRegistry
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/7/3 18:31
 */
@Service
public class InstanceRegistry {

    private static final Logger logger = LoggerFactory.getLogger(InstanceRegistry.class);

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock read = readWriteLock.readLock();
    private Lock write = readWriteLock.writeLock();

    private ConcurrentHashMap<String, Map<String, InstanceInfo>> registerInstanceInfos = new ConcurrentHashMap<>(64);


    public void register(InstanceInfo info) {
        try {
            read.lock();
            Map<String, InstanceInfo> infoMap = registerInstanceInfos.get(info.getAppName());
            if (infoMap == null) {
                final ConcurrentHashMap<String, InstanceInfo> newInfoMap = new ConcurrentHashMap<>(1);
                infoMap = registerInstanceInfos.putIfAbsent(info.getAppName(), newInfoMap);
                if (infoMap == null) {
                    infoMap = newInfoMap;
                }
            }
            infoMap.put(info.getAppName(), info);
            logger.info("register success:{}", info.getAppName());
        } finally {
            read.unlock();
        }
    }

    public void cancel() {

    }

    public void heartbeat(InstanceInfo info) {
        try {
            read.lock();
            Map<String, InstanceInfo> stringInstanceInfoMap = registerInstanceInfos.get(info.getAppName());
            if (stringInstanceInfoMap != null) {
                stringInstanceInfoMap.put(info.getInstanceId(), info);
            }
            Map<String, InstanceInfo> headData = registerInstanceInfos.putIfAbsent(info.getAppName(), stringInstanceInfoMap);
            if(headData==null){
                logger.info("heartBead hold");
            }
        } finally {
            read.unlock();
        }

    }

}
