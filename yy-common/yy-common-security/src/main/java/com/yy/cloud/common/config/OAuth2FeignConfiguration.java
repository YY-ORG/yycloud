package com.yy.cloud.common.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

import com.yy.cloud.common.interceptor.OAuth2FeignRequestInterceptor;

/**
 * 增加OAuth2对FeignClient的支持
 *
 * @author chenxj
 */
@Configuration
public class OAuth2FeignConfiguration {
    /**
     * oauth2FeignRequestInterceptor: 为FeignClient初始化Bean
     *
     * @param oAuth2ClientContext
     * @param resource
     * @return
     */
//    @Bean
//    public RequestInterceptor oauth2FeignRequestInterceptor(OAuth2ClientContext oAuth2ClientContext,
//                                                            OAuth2ProtectedResourceDetails resource) {
//        return new OAuth2FeignRequestInterceptor(oAuth2ClientContext, resource);
//    }
    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor(OAuth2ClientContext oAuth2ClientContext) {
        return new OAuth2FeignRequestInterceptor(oAuth2ClientContext);
    }
}
