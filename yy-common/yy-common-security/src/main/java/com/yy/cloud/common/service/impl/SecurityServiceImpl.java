package com.yy.cloud.common.service.impl;

import com.yy.cloud.common.clients.SecurityClient;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.otd.usermgmt.UserDetailsItem;
import com.yy.cloud.common.data.security.LDAPPrincipal;
import com.yy.cloud.common.service.SecurityService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;

/**
 * Created by chenxj on 12/10/16.
 */
@Service
@Slf4j
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private SecurityClient securityClient;

    @Override
    public UserDetailsItem getCurrentUser() {
        Authentication tempAuth = SecurityContextHolder.getContext().getAuthentication();
        if(tempAuth == null){
            log.warn("Could not get the sign in Authentication from the SecurityContextHolder...");
            return null;
        }
        Object tempObj = ((OAuth2Authentication) tempAuth).getPrincipal();
        if(tempObj == null){
            log.warn("Could not get the sign in User Name from the Authentication...");
            return null;
        }
        String loginName = null;
        GeneralContentResult<UserDetailsItem> tempResult = null;
//        if(tempObj instanceof LDAPPrincipal){
//            String tempId = ((LDAPPrincipal)tempObj).getId();
//            tempResult = this.securityClient.loadUserById(tempId);
//        } else {
            if (tempObj instanceof Principal){
                loginName = ((Principal)tempObj).getName();
            } else {
                loginName = tempObj.toString();
            }
            tempResult = this.securityClient.loadUserByUsernameOrId(loginName);
//        }

//        log.info("The result is: {}.", tempResult);
        if(!tempResult.getResultCode().equals(ResultCode.OPERATION_SUCCESS)) {
            log.info("Could not get the current user {} 's detail info currently....", loginName);
            return null;
        } else
            return tempResult.getResultContent();
    }
}
