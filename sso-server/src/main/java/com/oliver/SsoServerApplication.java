package com.oliver;

import com.oliver.controller.ServerTest;
import com.oliver.discovery.resources.ApplicationResources;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

/**
 * com.oliver SsoServerApplication
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/5/28 14:27
 */
@SpringBootApplication
public class SsoServerApplication {
    private final static Logger LOGGER = LoggerFactory.getLogger(SsoServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SsoServerApplication.class, args);
        LOGGER.info("SSO Server have already started.");
    }

//    @Bean
//    public ResourceConfig resourceConfig(){
//        Set<Class<?>> classs = new HashSet<>();
//        classs.add(ServerTest.class);
//        classs.add(ApplicationResources.class);
//        ResourceConfig config = new ResourceConfig();
//        config.registerClasses(classs);
//        return config;
//    }
}
