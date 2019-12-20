package com.oliver.appinfo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * com.oliver.appinfo InstanceInfo
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/12 17:20
 */
@Data
public class InstanceInfo {

    private static Logger logger = LoggerFactory.getLogger(InstanceInfo.class);

    private static final String COLON = ":";
    private static final String HTTPS_PROTOCOL = "https://";
    private static final String HTTP_PROTOCOL = "http://";

    private String instanceId;
    private String appName;
    private String appGroupName;
    private String ipAddr;
    private int port;
    private String homePageUrl;
    private InstanceStatus status;
    public InstanceInfo(){

    }

    @JsonCreator
    public InstanceInfo(@JsonProperty("instanceId") String instanceId,
                        @JsonProperty("appName") String appName,
                        @JsonProperty("appGroupName") String appGroupName,
                        @JsonProperty("ipAddr") String ipAddr,
                        @JsonProperty("port") int port,
                        @JsonProperty("homePageUrl") String homePageUrl) {
        this.instanceId = instanceId;
        this.appName = appName;
        this.appGroupName = appGroupName;
        this.ipAddr = ipAddr;
        this.port = port;
        this.homePageUrl = homePageUrl;
    }

    public enum InstanceStatus {
        /**
         * Ready to receive traffic
         */
        UP,
        /**
         * Do not send traffic- healthcheck callback failed
         */
        DOWN,
        /**
         * Just about starting- initializations to be done - do not
         */
        STARTING,
        /**
         * send traffic
         */
        OUT_OF_SERVICE,
        /**
         * Intentionally shutdown for traffic
         */
        UNKNOWN;

        public static InstanceStatus toEnum(String s) {
            if (s != null) {
                try {
                    return InstanceStatus.valueOf(s.toUpperCase());
                } catch (IllegalArgumentException e) {
                    // ignore and fall through to unknown
                    logger.debug("illegal argument supplied to InstanceStatus.valueOf: {}, defaulting to {}", s, UNKNOWN);
                }
            }
            return UNKNOWN;
        }
    }
}
