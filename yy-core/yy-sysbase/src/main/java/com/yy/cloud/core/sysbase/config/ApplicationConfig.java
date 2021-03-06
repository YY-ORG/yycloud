package com.yy.cloud.core.sysbase.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * ClassName: ApplicationConfig <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON -- Optional. <br/>
 * date: Jun 5, 2016 11:21:26 PM <br/>
 *
 * @author chenxj
 * @version 
 * @since JDK 1.8
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.yy.cloud.core.sysbase.data.repositories")
@EnableTransactionManagement
@EnableConfigurationProperties
public class ApplicationConfig {

    @Bean
    public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.yy.cloud.core.sysbase.data.domain");
        factory.setDataSource(dataSource);
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory(dataSource));
        return txManager;
    }
}
