package com.yy.cloud.core.usermgmt;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.yy.cloud.core.usermgmt.data.domain.FoxMenu;
import com.yy.cloud.core.usermgmt.data.domain.FoxPerm;
import com.yy.cloud.core.usermgmt.data.domain.FoxPermRole;
import com.yy.cloud.core.usermgmt.data.domain.FoxPermType;
import com.yy.cloud.core.usermgmt.data.domain.FoxRole;
import com.yy.cloud.core.usermgmt.data.domain.FoxRoleMenu;
import com.yy.cloud.core.usermgmt.data.domain.FoxUser;
import com.yy.cloud.core.usermgmt.data.domain.FoxUserRole;

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
@EnableFeignClients(basePackages = {"com.yy.cloud.core.usermgmt.clients"})
@ComponentScan({"com.yy.cloud.core.usermgmt"})
@EnableJpaRepositories
@EnableResourceServer
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private RepositoryRestMvcConfiguration repositoryRestConfiguration;

    @PostConstruct
    public void postConstructConfiguration() {

        repositoryRestConfiguration.config().exposeIdsFor(FoxUser.class);
        repositoryRestConfiguration.config().exposeIdsFor(FoxUserRole.class);
        repositoryRestConfiguration.config().exposeIdsFor(FoxRole.class);
        repositoryRestConfiguration.config().exposeIdsFor(FoxRoleMenu.class);
        repositoryRestConfiguration.config().exposeIdsFor(FoxPermType.class);
        repositoryRestConfiguration.config().exposeIdsFor(FoxPermRole.class);
        repositoryRestConfiguration.config().exposeIdsFor(FoxPerm.class);
        repositoryRestConfiguration.config().exposeIdsFor(FoxMenu.class);
    }
}
