/**
 * Project Name:liz-edge-server
 * File Name:Application.java
 * Package Name:com.gemii.lizcloud.baseplatform.edge
 * Date:Sep 19, 20162:16:10 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 */

package com.yy.cloud.baseplatform.edge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonCommandFactory;
import org.springframework.cloud.netflix.zuul.filters.route.apache.HttpClientRibbonCommandFactory;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * ClassName:Application <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Sep 19, 2016 2:16:10 PM <br/>
 *
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */

@SpringBootApplication
@EnableDiscoveryClient
//@ComponentScan({ "com.gemii.lizcloud.baseplatform", "com.gemii.lizcloud.common"
// })
@EnableZuulProxy
@EnableOAuth2Sso
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    //    @Bean
//    @Primary
//    public OAuth2ClientContextFilter dynamicOauth2ClientContextFilter() {
//        return new DynamicOauth2ClientContextFilter();
//    }
//    @Bean
//    public RibbonCommandFactory<?> ribbonCommandFactory(final SpringClientFactory clientFactory) {
//        return new HttpClientRibbonCommandFactory(clientFactory);
//    }

//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowCredentials(Boolean.valueOf(true));
//        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//        return new CorsFilter(urlBasedCorsConfigurationSource);
//    }
}
