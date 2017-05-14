package com.yy.cloud.baseplatform.hystrix;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yy.cloud.baseplatform.hystrix.stream.MockStreamServlet;

/**
 * ClassName: HystrixDashboardApplication <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON -- Optional. <br/>
 * date: Sep 5, 2016 1:40:55 PM <br/>
 *
 * @author chenxj
 * @version 
 * @since JDK 1.8
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@Controller
@EnableDiscoveryClient
@EnableHystrixDashboard
@EnableTurbine
public class HystrixDashboardApplication extends SpringBootServletInitializer {
	
	@RequestMapping("/")
	public String home() {
		return "forward:/hystrix";
	}

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HystrixDashboardApplication.class).web(true);
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(HystrixDashboardApplication.class).web(true).run(args);
    }

    @Bean
    public ServletRegistrationBean mockStreamServlet() {
        return new ServletRegistrationBean(new MockStreamServlet(), "/mock.stream");
    }

}
