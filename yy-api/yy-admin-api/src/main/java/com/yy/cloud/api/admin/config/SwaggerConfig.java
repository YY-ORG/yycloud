/**
 * Project Name:liz-admin
 * File Name:SwaggerConfig.java
 * Package Name:com.gemii.lizcloud.api.admin.config
 * Date:Sep 19, 20168:48:11 AM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.api.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * ClassName:SwaggerConfig <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Sep 19, 2016 8:48:11 AM <br/>
 * 
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	/*@Bean
	public Docket swaggerSpringMvcPlugin() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("authsec").apiInfo(apiInfo())
				.genericModelSubstitutes(DeferredResult.class).useDefaultResponseMessages(false).forCodeGeneration(true)
				.select().apis(RequestHandlerSelectors.basePackage("com.yy.cloud.api.admin.controller"))
				.paths(PathSelectors.any()).build();
	}*/

	
	@Bean
	public Docket swaggerSpringMvcPlugin() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("authsec")
				.genericModelSubstitutes(DeferredResult.class).useDefaultResponseMessages(false).forCodeGeneration(true).select().apis(RequestHandlerSelectors.basePackage("com.yy.cloud.api.admin.controller"))
				.paths(PathSelectors.any()).build()
				.apiInfo(apiInfo());
	}
	 

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("AdminUI Management").description("YYcloud Adminui Management  API")
				.contact(new Contact("GHL", "http://www.google.com", "ghlnmm@126.com")).version("0.1").build();
	}
}
