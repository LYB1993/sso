package com.oliver.test.jersey;

import com.netflix.discovery.shared.transport.jersey.ApacheHttpClientConnectionCleaner;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.client.apache4.ApacheHttpClient4;
import com.sun.jersey.client.apache4.config.DefaultApacheHttpClient4Config;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.http.params.HttpConnectionParams.*;

/**
 * com.oliver.test.jersey SsoJerseyClientImpl
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/26 18:37
 */
public class SsoJerseyClientImpl implements SsoJerseyClient {

    private static final Logger logger = LoggerFactory.getLogger(SsoJerseyClientImpl.class);

    private ApacheHttpClient4 apacheHttpClient;
    private ApacheHttpClientConnectionCleaner apacheHttpClientConnectionCleaner;

    private ClientConfig jerseyClientConfig;

    public SsoJerseyClientImpl() {
        logger.info("client init.....");
        jerseyClientConfig = new MyDefaultApacheHttpClient4Config();
        apacheHttpClient = ApacheHttpClient4.create(jerseyClientConfig);
        HttpParams params = apacheHttpClient.getClientHandler().getHttpClient().getParams();
        setConnectionTimeout(params, 200);
        setSoTimeout(params, 200);
        apacheHttpClientConnectionCleaner = new ApacheHttpClientConnectionCleaner(apacheHttpClient, 200);
    }

    @Override
    public ApacheHttpClient4 getClient() {
        return apacheHttpClient;
    }

    @Override
    public void destroyResources() {
        apacheHttpClientConnectionCleaner.shutdown();
        apacheHttpClient.destroy();
    }

    class MyDefaultApacheHttpClient4Config extends DefaultApacheHttpClient4Config {


    }
}
