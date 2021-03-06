package com.yy.cloud.core.sysbase;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import com.yy.cloud.core.sysbase.data.domain.YYSystemdictionary;

/**
 * ClassName: Application <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON -- Optional. <br/>
 * date: May 31, 2016 10:02:11 PM <br/>
 *
 * @author chenxj
 * @version 
 * @since JDK 1.8
 */
@SpringBootApplication
@ComponentScan
@EnableDiscoveryClient
//@EnableZuulProxy
//@EnableHystrix
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private RepositoryRestMvcConfiguration repositoryRestConfiguration;

	@PostConstruct
	public void postConstructConfiguration() {
		// repositoryRestConfiguration.objectMapper().registerModule(new
		// Jackson2HalModule());

		repositoryRestConfiguration.config().exposeIdsFor(YYSystemdictionary.class);
	}
	
}
