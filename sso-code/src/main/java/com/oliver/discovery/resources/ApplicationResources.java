package com.oliver.discovery.resources;

import com.oliver.appinfo.InstanceInfo;
import com.oliver.discovery.InstanceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * com.oliver.discovery.resources ApplicationResources
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/7/3 11:03
 */
@Component
@Path("/api")
public class ApplicationResources {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationResources.class);

    private static final String HEADER_REPLICATION = "o-netflix-replication";

    @Resource(name = "instanceRegistry")
    private InstanceRegistry registry;


    @Path("/register")
    @POST
    @Consumes({"application/json", "application/xml"})
    public void register(InstanceInfo instanceInfo, @HeaderParam(HEADER_REPLICATION) String isReplication) {
        logger.info("instanceInfo:{},Replication:{}", instanceInfo, isReplication);
        registry.register(instanceInfo);

    }

}
