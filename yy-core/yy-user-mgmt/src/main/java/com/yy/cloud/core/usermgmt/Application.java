package com.yy.cloud.core.usermgmt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.yy.cloud.core.usermgmt.data.domain.*;

import javax.annotation.PostConstruct;

/**
 * ClassName: Application <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON -- Optional. <br/>
 * date: Jul 18, 2016 11:17:59 AM <br/>
 *
 * @author chenxj
 * @since JDK 1.8
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.gemii.lizcloud.core.usermgmt.clients",
        "com.gemii.lizcloud.common.clients"})
@ComponentScan({"com.gemii.lizcloud"})
@EnableResourceServer
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

        repositoryRestConfiguration.config().exposeIdsFor(FoxUser.class);
        repositoryRestConfiguration.config().exposeIdsFor(FoxUserRole.class);
        repositoryRestConfiguration.config().exposeIdsFor(FoxTenant.class);
        repositoryRestConfiguration.config().exposeIdsFor(FoxRole.class);
        repositoryRestConfiguration.config().exposeIdsFor(FoxPermType.class);
        repositoryRestConfiguration.config().exposeIdsFor(FoxPermRole.class);
        repositoryRestConfiguration.config().exposeIdsFor(FoxPerm.class);
        repositoryRestConfiguration.config().exposeIdsFor(FoxOrgRole.class);
        repositoryRestConfiguration.config().exposeIdsFor(FoxOrganization.class);
        repositoryRestConfiguration.config().exposeIdsFor(FoxMenu.class);
        repositoryRestConfiguration.config().exposeIdsFor(FoxAccess.class);
    }
}
