package com.yy.cloud.common.data.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Created by guof on 2016/11/14.
 */
public class LizCurrentUser extends User {

    private String userId;

    private String userName;

    private String organizationId;

    public LizCurrentUser(String username,
                          String password,
                          boolean enabled,
                          boolean accountNonExpired,
                          boolean credentialsNonExpired,
                          boolean accountNonLocked,
                          Collection<? extends GrantedAuthority> authorities,
                          String userId,
                          String userName,
                          String organizationId
    ) {
        super(username,
                password,
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                authorities);

        this.userId = userId;
        this.userName = userName;
        this.organizationId = organizationId;
    }
}
