package com.yy.cloud.core.usermgmt;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import com.yy.cloud.core.usermgmt.data.domain.YYMenu;
import com.yy.cloud.core.usermgmt.data.domain.YYOrganization;
import com.yy.cloud.core.usermgmt.data.domain.YYRole;
import com.yy.cloud.core.usermgmt.data.domain.YYRoleMenu;
import com.yy.cloud.core.usermgmt.data.domain.YYUser;
import com.yy.cloud.core.usermgmt.data.domain.YYUserInfo;
import com.yy.cloud.core.usermgmt.data.domain.YYUserOrganization;
import com.yy.cloud.core.usermgmt.data.domain.YYUserRole;

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
@EnableFeignClients(basePackages = {"com.yy.cloud.core.usermgmt.clients","com.yy.cloud.common.clients"})
@ComponentScan({"com.yy.cloud"})
//@EnableResourceServer
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private RepositoryRestMvcConfiguration repositoryRestConfiguration;

    @PostConstruct
	public void postConstructConfiguration() {
		repositoryRestConfiguration.config().exposeIdsFor(YYMenu.class);
		repositoryRestConfiguration.config().exposeIdsFor(YYOrganization.class);
		repositoryRestConfiguration.config().exposeIdsFor(YYRole.class);
		repositoryRestConfiguration.config().exposeIdsFor(YYRoleMenu.class);
		repositoryRestConfiguration.config().exposeIdsFor(YYUser.class);
		repositoryRestConfiguration.config().exposeIdsFor(YYUserInfo.class);
		repositoryRestConfiguration.config().exposeIdsFor(YYUserOrganization.class);
		repositoryRestConfiguration.config().exposeIdsFor(YYUserRole.class);
	}
}
