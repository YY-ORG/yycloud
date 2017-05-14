package com.yy.cloud.baseplatform.authserver.component;

import com.yy.cloud.common.clients.SecurityClient;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.otd.usermgmt.AdLoginReq;
import com.yy.cloud.common.data.security.LDAPPrincipal;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by chenxj on 12/14/16.
 */
@Component("customLDAPAuthenticationProvider")
@Slf4j
public class CustomLDAPAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private SecurityClient securityClient;

    @Value("${foxcloud.security.ldap_salt}")
    private String securitySalt;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String credential = (String) authentication.getCredentials();

        String tempSalt = "$" + securitySalt + "$";

        int index = credential.indexOf(tempSalt);
        String password = "";
        String ldapId = null;
        if(index < 0){
            password = credential;
        } else {
            password = credential.substring(0, index);
            ldapId = credential.substring(index + tempSalt.length());
        }
        AdLoginReq tempReq = new AdLoginReq();
        tempReq.setLogin(userName);
        tempReq.setPassword(password);
        tempReq.setLdapId(ldapId);
        log.info("Going to auth user: {}", tempReq);
        GeneralContentResult<String> tempResult = this.securityClient.loginAdUser(tempReq);
        log.info("Response of login is : {}", tempResult);
        if(tempResult.getResultCode().equals(ResultCode.OPERATION_SUCCESS)){
            String foxUserId = tempResult.getResultContent();

            LDAPPrincipal tempPrincipal = new LDAPPrincipal();
            tempPrincipal.setId(foxUserId);
            tempPrincipal.setName(userName);
            Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
            return new UsernamePasswordAuthenticationToken(foxUserId, password, authorities);
        }
        throw new BadCredentialsException("Username not found.");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
