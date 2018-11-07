package com.yy.cloud.core.assess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

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
@ComponentScan({"com.yy.cloud"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.yy.cloud.core.assess.clients", "com.yy.cloud.common.clients"})
@EnableResourceServer
@EnableScheduling
//@EnableHystrix
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	@Autowired
//	private RepositoryRestMvcConfiguration repositoryRestConfiguration;
//
//	@PostConstruct
//	public void postConstructConfiguration() {
//		// repositoryRestConfiguration.objectMapper().registerModule(new
//		// Jackson2HalModule());
//
//		repositoryRestConfiguration.config().exposeIdsFor(YYSystemdictionary.class);
//	}
	
}
