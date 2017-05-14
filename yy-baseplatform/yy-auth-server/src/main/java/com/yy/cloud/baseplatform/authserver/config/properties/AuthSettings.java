package com.yy.cloud.baseplatform.authserver.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by chenxj on 12/2/16.
 */
@Data
@ConfigurationProperties(prefix = "foxauth.token.settings")
public class AuthSettings {
    /**
     * token的有效时间, 默认300秒
     */
    private int validity_seconds = 300;
}
