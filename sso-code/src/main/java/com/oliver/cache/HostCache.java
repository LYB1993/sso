package com.oliver.cache;

import com.oliver.bean.eo.Constant;
import com.oliver.bean.vo.HostInfo;
import com.oliver.bean.vo.Result;
import com.oliver.configure.SsoServerProperties;
import com.oliver.service.IRedisService;
import com.oliver.util.InetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * com.oliver.cache HostCache
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/10 16:47
 */
@Component
public class HostCache {

    private final Logger log = LoggerFactory.getLogger(HostCache.class);

    private static List<HostInfo> list = new ArrayList<>();

    private final ExecutorService executorService;

    HostCache() {
        this.executorService = Executors.newCachedThreadPool(r -> {
            Thread thread = new Thread(r);
            thread.setName(Constant.THREAD_PREFIX.getValue());
            thread.setDaemon(true);
            return thread;
        });
    }

    @Resource
    private IRedisService redisService;
    @Resource
    private SsoServerProperties ssoServerProperties;

    public Result<List> getClientList() {
        if (!list.isEmpty()) {
            return new Result<List>().success(list);
        }
        initHostCache();
        return new Result<List>().success(list);
    }

    @PostConstruct
    public void initCache() {
        initHostCache();
    }

    /**
     * 初始化的时候验证地址是否可用
     */
    private void initHostCache() {
        if (!ssoServerProperties.isSsoServer()) {
            return;
        }
        Set<String> mget = redisService.mget(Constant.KEY_CLIENTS.getValue());
        mget.forEach(str -> {
            String[] split = str.split(Constant.SEPARATOR_URL.getValue());
            if (split.length == 2) {
                String[] split1 = split[1].split(":");
                HostInfo hostInfo = new HostInfo();
                hostInfo.setIpAddress(split1[0]);
                hostInfo.setPort(Integer.valueOf(split1[1]));
                hostInfo.setHostname(split[0]);
                list.add(hostInfo);
            }
        });
        list.forEach(hostInfo -> {
            Future<Boolean> submit = executorService.submit(new Task(hostInfo));
            try {
                hostInfo.setUsAble(submit.get());
            } catch (InterruptedException | ExecutionException e) {
                log.error("client list init is failed.hostInfo: {}:{}",
                        hostInfo.getIpAddress(), hostInfo.getPort());
            }
        });
    }

    static class Task implements Callable<Boolean> {
        private HostInfo hostInfo;

        Task(HostInfo hostInfo) {
            this.hostInfo = hostInfo;
        }

        @Override
        public Boolean call() {
            return InetUtils.isHostConnectAble(hostInfo.getIpAddress(),
                    hostInfo.getPort());
        }
    }
}
