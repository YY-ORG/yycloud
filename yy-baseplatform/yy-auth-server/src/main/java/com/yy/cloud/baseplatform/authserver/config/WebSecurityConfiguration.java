package com.yy.cloud.baseplatform.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.yy.cloud.baseplatform.authserver.component.CustomLDAPAuthenticationProvider;

@Configuration
//@EnableWebSecurity
@Order(-1)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    @Qualifier("customLDAPAuthenticationProvider")
    private AuthenticationProvider customLDAPAuthenticationProvider;

    @Autowired
    private DaoAuthenticationProvider daoAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
/*        http.antMatcher("*").authorizeRequests() // all requests are protected by default
                .antMatchers("/", "/login**", "/webjars*").permitAll() // the home page and login endpoints are explicitly excluded
                .anyRequest().authenticated() // all other endpoints require an authenticated user
                .and()
                .csrf().disable();*/
        http.anonymous().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
           //     .formLogin().loginPage("/login").permitAll()
//                .and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS, "/oauth/token").permitAll()
                .and()
                .requestMatchers()
                .antMatchers("/", "/login", "/logout", "/oauth/authorize", "/oauth/confirm_access")
                .and().authorizeRequests()
                .anyRequest().authenticated()
                .and().logout().logoutUrl("/logout").
                logoutSuccessUrl("/").permitAll()
                .and()
                .csrf()
                .disable();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.customLDAPAuthenticationProvider);
        auth.authenticationProvider(this.daoAuthenticationProvider);

    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth.userDetailsService(userDetailsService)
////                .passwordEncoder(new BCryptPasswordEncoder());
////        auth.authenticationProvider(this.daoAuthenticationProvider);
//
//        auth.authenticationProvider(this.customLDAPAuthenticationProvider);
//      //  auth.authenticationProvider(customLDAPAuthenticationProvider);
//     //   auth.parentAuthenticationManager(this.authenticationManager());
//    }

    @Bean
    public DaoAuthenticationProvider getDaoAuthProvider(){
        DaoAuthenticationProvider tempProvider = new DaoAuthenticationProvider();
        tempProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        tempProvider.setUserDetailsService(this.userDetailsService);
        return tempProvider;
    }

}
