package com.yy.cloud.baseplatform.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * ClassName: AuthserverApplication <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON -- Optional. <br/>
 * date: Sep 6, 2016 1:13:44 PM <br/>
 *
 * @author chenxj
 * @since JDK 1.8
 */
@SpringBootApplication
@EnableDiscoveryClient
//@EnableResourceServer
@ComponentScan(basePackages = {"com.yy.cloud.baseplatform", "com.yy.cloud.common.service"})
@EnableFeignClients(basePackages = "com.yy.cloud.common.clients")
public class AuthserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthserverApplication.class, args);
    }

}