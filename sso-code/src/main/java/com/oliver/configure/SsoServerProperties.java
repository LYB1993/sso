package com.oliver.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * com.oliver.configure SsoServerProperties
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/6 10:15
 */
@Configuration
@ConfigurationProperties(prefix = "spring.sso-server")
@Data
public class SsoServerProperties {

    /**
     * is sso server.default value:false
     * This property is for judgment purposes
     * sso-url{@link SsoProperties#ssoUrl} property No configuration,
     * make some judgments.
     */
    private boolean ssoServer = false;
    /**
     * leaseTime.default value: 30minutes
     */
    private Long redisLeaseTime = 30L;
    /**
     * timeUnit.default value:minutes
     */
    private TimeUnit redisTimeUnit = TimeUnit.MINUTES;

    /**
     * cookie Lease Time ,default value: 1H
     */
    private Long cookieLeaseTime = 60L;

    /**
     * cookie TimeUnit.default value:minutes
     */
    private TimeUnit cookieTimeUnit = TimeUnit.MINUTES;

    public Long getRedisLeaseTime() {
        return redisTimeUnit.toSeconds(redisLeaseTime);
    }

    public Long getCookieLeaseTime() {
        return cookieTimeUnit.toSeconds(cookieLeaseTime);
    }
}
