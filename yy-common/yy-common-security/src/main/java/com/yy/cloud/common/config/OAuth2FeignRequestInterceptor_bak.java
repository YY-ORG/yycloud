package com.yy.cloud.common.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.Arrays;

/**
 * Created by chenxj on 12/13/16.
 */
@Slf4j
public class OAuth2FeignRequestInterceptor_bak implements RequestInterceptor {
    public static final String BEARER = "Bearer";
    public static final String AUTHORIZATION = "Authorization";
    private final OAuth2ClientContext oAuth2ClientContext;
    private final OAuth2ProtectedResourceDetails resource;
    private final String tokenType;
    private final String header;
    private AccessTokenProvider accessTokenProvider;

    public OAuth2FeignRequestInterceptor_bak(OAuth2ClientContext oAuth2ClientContext, OAuth2ProtectedResourceDetails resource) {
        this(oAuth2ClientContext, resource, "Bearer", "Authorization");
    }

    public OAuth2FeignRequestInterceptor_bak(OAuth2ClientContext oAuth2ClientContext, OAuth2ProtectedResourceDetails resource, String tokenType, String header) {
        this.accessTokenProvider = new AccessTokenProviderChain(Arrays.asList(new AccessTokenProvider[]{new AuthorizationCodeAccessTokenProvider(), new ImplicitAccessTokenProvider(), new ResourceOwnerPasswordAccessTokenProvider(), new ClientCredentialsAccessTokenProvider()}));
        this.oAuth2ClientContext = oAuth2ClientContext;
        this.resource = resource;
        this.tokenType = tokenType;
        this.header = header;
    }

    public void apply(RequestTemplate template) {//modified by xuejin for /noauth/** Feign Request
        String tempTokenValue = this.extract(this.tokenType);
        log.info("The Fiegn Token is: {}", tempTokenValue);
        if(tempTokenValue != null)
            template.header(this.header, new String[]{tempTokenValue});
    }

    protected String extract(String tokenType) {
        OAuth2AccessToken accessToken = this.getToken();
        if(accessToken == null){
            return null;
        }
        return String.format("%s %s", new Object[]{tokenType, accessToken.getValue()});
    }

    public OAuth2AccessToken getToken() {
        OAuth2AccessToken accessToken = this.oAuth2ClientContext.getAccessToken();
        if(accessToken == null || accessToken.isExpired()) {
            try {
                accessToken = this.acquireAccessToken();
            } catch (UserRedirectRequiredException var5) {
                this.oAuth2ClientContext.setAccessToken((OAuth2AccessToken)null);
                String stateKey = var5.getStateKey();
                if(stateKey != null) {
                    Object stateToPreserve = var5.getStateToPreserve();
                    if(stateToPreserve == null) {
                        stateToPreserve = "NONE";
                    }

                    this.oAuth2ClientContext.setPreservedState(stateKey, stateToPreserve);
                }

                throw var5;
            }
        }

        return accessToken;
    }

    protected OAuth2AccessToken acquireAccessToken() throws UserRedirectRequiredException {
        AccessTokenRequest tokenRequest = this.oAuth2ClientContext.getAccessTokenRequest();
        if(tokenRequest == null) {
            throw new AccessTokenRequiredException("Cannot find valid context on request for resource \'" + this.resource.getId() + "\'.", this.resource);
        } else {
            String stateKey = tokenRequest.getStateKey();
            if(stateKey != null) {
                tokenRequest.setPreservedState(this.oAuth2ClientContext.removePreservedState(stateKey));
            }

            OAuth2AccessToken existingToken = this.oAuth2ClientContext.getAccessToken();
            if(existingToken != null) {
                this.oAuth2ClientContext.setAccessToken(existingToken);
            }

            OAuth2AccessToken obtainableAccessToken = this.accessTokenProvider.obtainAccessToken(this.resource, tokenRequest);
            if(obtainableAccessToken != null && obtainableAccessToken.getValue() != null) {
                this.oAuth2ClientContext.setAccessToken(obtainableAccessToken);
                return obtainableAccessToken;
            } else {//modified by xuejin
             //   throw new IllegalStateException(" Access token provider returned a null token, which is illegal according to the contract.");
                return obtainableAccessToken;// Return null, at this time.
            }
        }
    }
}
