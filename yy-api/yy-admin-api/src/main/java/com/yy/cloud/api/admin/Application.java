/**
 * Project Name:liz-admin
 * File Name:Application.java
 * Package Name:com.gemii.lizcloud.api.admin
 * Date:Sep 18, 20164:15:04 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 */

package com.yy.cloud.api.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.config.EnableHypermediaSupport;

import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * ClassName:Application <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     Sep 18, 2016 4:15:04 PM <br/>
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.gemii.lizcloud.api.admin.clients", "com.gemii.lizcloud.common.clients"})
//@EnableHystrix
@ComponentScan({"com.gemii.lizcloud"})
@EnableDiscoveryClient
@EnableHystrix
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@EnableResourceServer
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//	@Bean
//	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
//		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
//		jsonConverter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
//		
//		objectMapper.registerModule(new Jackson2HalModule());
//		jsonConverter.setObjectMapper(objectMapper);
//		return jsonConverter;
//	}
}

