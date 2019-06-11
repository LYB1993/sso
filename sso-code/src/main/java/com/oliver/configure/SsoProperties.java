package com.oliver.configure;

import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * com.oliver.configure SsoProperties
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/5/30 16:16
 */
@Configuration
@ConfigurationProperties(prefix = "spring.sso")
@Data
public class SsoProperties {

    /**
     * sso url
     */
    private String ssoUrl;

    /**
     * exclude path
     */
    private List<String> excludePath;
    /**
     * host name.default value: localhost
     * sso server page list display name.
     */

    private String hostName = "localhost";

    /**
     * host address.default value:127.0.0.1
     * sso server page list display address.
     */
    private String hostAddress = "127.0.0.1";


}
