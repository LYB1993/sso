package com.oliver.configure.intercept;

import com.oliver.bean.eo.Constant;
import com.oliver.configure.SsoProperties;
import com.oliver.configure.SsoServerProperties;
import com.oliver.service.IRedisService;
import com.oliver.util.InetUtils;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * com.oliver.configure.intercept InterceptConfig
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/5 15:19
 */
@Component
public class InterceptConfig implements WebMvcConfigurer {

    @Resource
    private SsoIntercept ssoIntercept;

    @Resource
    private SsoProperties ssoProperties;

    @Resource
    private SsoServerProperties ssoServerProperties;

    @Resource
    private ServerProperties serverProperties;

    @Resource
    private IRedisService redisService;

    private final static List<String> DEFAULT_EXCLUDE_PATH = new ArrayList<>();

    static {
        DEFAULT_EXCLUDE_PATH.add("/js/**");
        DEFAULT_EXCLUDE_PATH.add("/**/*.ico");
        DEFAULT_EXCLUDE_PATH.add("/login");
        DEFAULT_EXCLUDE_PATH.add("/login.html");
        DEFAULT_EXCLUDE_PATH.add("/error");
        DEFAULT_EXCLUDE_PATH.add("/toindex");
        DEFAULT_EXCLUDE_PATH.add("/api/**");
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePath = ssoProperties.getExcludePath();
        if (excludePath != null && !excludePath.isEmpty()) {
            DEFAULT_EXCLUDE_PATH.addAll(excludePath);
        }
        registry.addInterceptor(ssoIntercept)
                .addPathPatterns("/**")
                .excludePathPatterns(DEFAULT_EXCLUDE_PATH);
    }

    @PostConstruct
    public void registerClientInfo() {
        if (ssoServerProperties.isSsoServer()) {
            return;
        }
        InetAddress clientAddress = InetUtils.getClientAddress();
        int port = serverProperties.getPort();
        String hostName;
        String hostAddress;
        if (clientAddress != null) {
            hostName = clientAddress.getHostName();
            hostAddress = Constant.SEPARATOR_URL.getValue().concat(clientAddress.getHostAddress());
        } else {
            hostName = ssoProperties.getHostName();
            hostAddress = ssoProperties.getHostAddress();
        }
        redisService.sadd(Constant.KEY_CLIENTS.getValue(), hostName + hostAddress + ":" + port);
    }

}
