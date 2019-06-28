package com.oliver.controller;

import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;

/**
 * com.oliver.configtest ServerTest
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/25 09:58
 */
@Component
@Path("/api")
public class ServerTest {
    private static Logger log = LoggerFactory.getLogger(ServerTest.class);

    @Bean
    public ResourceConfig resourceConfig(){
        ResourceConfig config = new ResourceConfig();
        config.register(ServerTest.class);
        return config;
    }


    @GET
    @Path("test")
    public String getTest(@QueryParam("version") String version) {
        log.info("version:{}", version);
        return version;
    }
}
