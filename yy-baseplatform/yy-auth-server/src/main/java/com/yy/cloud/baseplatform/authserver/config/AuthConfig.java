package com.yy.cloud.baseplatform.authserver.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.yy.cloud.baseplatform.authserver.config.properties.AuthSettings;

@Configuration
@EnableConfigurationProperties(AuthSettings.class)
public class AuthConfig {

}
