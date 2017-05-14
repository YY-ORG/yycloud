package com.yy.cloud.common.service;

import com.yy.cloud.common.clients.SecurityClient;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.otd.tenant.OrganizationItem;
import com.yy.cloud.common.data.otd.usermgmt.UserDetailsItem;
import com.yy.cloud.common.data.security.LizCurrentUser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private SecurityClient securityClient;

    @Override
    public UserDetails loadUserByUsername(String _username) throws UsernameNotFoundException {
        log.debug("Going to load user info by username {}", _username);
        GeneralContentResult<UserDetailsItem> response = securityClient.loadUserByLoginName(_username);

        if (!ResultCode.OPERATION_SUCCESS.equals(response.getResultCode())) {
            log.info("User does not exists with username {}.", _username);
            throw new UsernameNotFoundException(_username);
        }
        log.info("The response from usermgmt is {}.", response);
        UserDetailsItem userDetailsItem = Optional.ofNullable(response.getResultContent())
                .orElseThrow(() -> new UsernameNotFoundException(_username));

        List<GrantedAuthority> authorities = new ArrayList<>();
        userDetailsItem.getRoles().forEach(
                roleItem -> authorities.add(new SimpleGrantedAuthority(roleItem.getRoleName()))
        );

        String organizationId = "";
        if (userDetailsItem.getOrganizations() != null && !userDetailsItem.getOrganizations().isEmpty()) {
            OrganizationItem organizationItem = userDetailsItem.getOrganizations().get(0);
            organizationId = organizationItem.getId();
        }

        LizCurrentUser currentUser = new LizCurrentUser(
                _username,
                userDetailsItem.getPassword(),
                true,
                true,
                true,
                true,
                authorities,
                userDetailsItem.getUserId(),
                userDetailsItem.getUserName(),
                organizationId);
        log.debug("Load user info {} ", currentUser);
        return currentUser;
    }

}
