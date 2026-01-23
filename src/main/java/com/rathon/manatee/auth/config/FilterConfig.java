package com.rathon.manatee.auth.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<RawBodyLoggingFilter> rawBodyLoggingFilter() {
        FilterRegistrationBean<RawBodyLoggingFilter> reg = new FilterRegistrationBean<>();
        reg.setFilter(new RawBodyLoggingFilter());
        reg.addUrlPatterns("/api/auth/mfa/callback");
        reg.setOrder(1);
        return reg;
    }
}
