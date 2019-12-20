package com.oliver;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * com.oliver.client SsoSpringApplication
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/5 17:59
 */
@SpringBootApplication
public class SsoClientSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(SsoClientSpringApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean singleSignOutFilter() {
        FilterRegistrationBean<SingleSignOutFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new SingleSignOutFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        registration.setName("singleSignOutFilter");
        return registration;
    }

    @Bean
    public ServletListenerRegistrationBean singleSignOutHttpSessionListener() {
        return new ServletListenerRegistrationBean<>(new SingleSignOutHttpSessionListener());
    }
}
