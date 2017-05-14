/**
 * Project Name:liz-admin
 * File Name:SwaggerConfig.java
 * Package Name:com.gemii.lizcloud.api.admin.config
 * Date:Sep 19, 20168:48:11 AM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.core.usermgmt.config;

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
	@Bean
	public Docket swaggerSpringMvcPlugin() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("usermgmt")
				.apiInfo(apiInfo())
				.genericModelSubstitutes(DeferredResult.class)
				.useDefaultResponseMessages(false)
				.forCodeGeneration(true)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.gemii.lizcloud.core.usermgmt.controller"))
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("foxcloud usermgmt management")
				.description("fox usermgmt core API")
				.contact(new Contact("Fred Cai", "http://www.google.com", "huanfeng@hpe.com"))
				.version("0.1")
				.build();
	}
//	@Bean
//	public Docket swaggerSpringMvcPlugin() {
//		return new Docket(DocumentationType.SWAGGER_2).groupName("authsec")
//				.genericModelSubstitutes(DeferredResult.class).useDefaultResponseMessages(false).forCodeGeneration(true)
//				.pathMapping("/").select().paths(PathSelectors.regex("/authsec/.*")).build()
//				.apiInfo(apiInfo());
//	}
//
//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder().title("Paltform Management").description("Foxcloud Platform Management core API")
//				.contact(new Contact("XJ CHEN", "http://www.google.com", "xue-jin.chen@hpe.com")).version("0.1").build();
//	}
//
//	@Bean
//	public Docket swaggerSpringMvcPluginTenant() {
//		return new Docket(DocumentationType.SWAGGER_2).groupName("tenant")
//				.genericModelSubstitutes(DeferredResult.class).useDefaultResponseMessages(false).forCodeGeneration(true)
//				.pathMapping("/").select().paths(PathSelectors.regex("/tenant/.*")).build()
//				.apiInfo(apiInfoTenant());
//	}
//
//	private ApiInfo apiInfoTenant() {
//		return new ApiInfoBuilder().title("user Management").description("user Management")
//				.contact(new Contact("XJ CHEN", "http://www.google.com", "xue-jin.chen@hpe.com")).version("0.1").build();
//	}
//
//	@Bean
//	public Docket swaggerSpringMvcPluginNoAuth() {
//		return new Docket(DocumentationType.SWAGGER_2).groupName("/noauth")
//				.genericModelSubstitutes(DeferredResult.class).useDefaultResponseMessages(false).forCodeGeneration(true)
//				.pathMapping("/").select().paths(PathSelectors.regex("/noauth/.*")).build()
//				.apiInfo(apiInfoNoAuth());
//	}
//
//	private ApiInfo apiInfoNoAuth() {
//		return new ApiInfoBuilder().title("user Management").description("user Management")
//				.contact(new Contact("XJ CHEN", "http://www.google.com", "xue-jin.chen@hpe.com")).version("0.1").build();
//	}

}
