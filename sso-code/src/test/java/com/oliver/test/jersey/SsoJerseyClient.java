package com.oliver.test.jersey;

import com.sun.jersey.client.apache4.ApacheHttpClient4;

/**
 * com.oliver.test.jersey SsoJerseyClient
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/26 18:35
 */
public interface SsoJerseyClient {

    /**
     * create client
     *
     * @return client
     */
    ApacheHttpClient4 getClient();


    /**
     * Clean up resources.
     */
    void destroyResources();
}
