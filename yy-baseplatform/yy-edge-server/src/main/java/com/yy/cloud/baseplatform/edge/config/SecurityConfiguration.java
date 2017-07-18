package com.yy.cloud.baseplatform.edge.config;

import java.io.IOException;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@Configuration
@EnableOAuth2Sso
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String CSRF_COOKIE_NAME = "XSRF-TOKEN";
	private static final String CSRF_ANGULAR_HEADER_NAME = "X-XSRF-TOKEN";

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		log.debug("The value of http is \'"+http+"\'");
		
		http.logout().and().exceptionHandling()
				.authenticationEntryPoint(new Http401AuthenticationEntryPoint("Session realm=\"SESSION\""))//
				.and()//
				.antMatcher("/**").authorizeRequests()//
			//	.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.antMatchers("/webjars/**", "/", "/**/*.html", "/assets/**", "/css/**", "/js/**", "/index.html", "/home.html", "/login.html", "/uaa/**")
				.permitAll()//
				.antMatchers("/*/webjars/**").permitAll()//
				.antMatchers("/*/swagger-resources/**").permitAll()//
				.antMatchers("/*/v2/**").permitAll()//
				.antMatchers("/*/noauth/**").permitAll()//
				.antMatchers("/*/swagger-ui.html/**").permitAll()//
//				.antMatchers("/*/swagger-ui.html#").permitAll()//
				.antMatchers("/*/swagger-ui.html#/**").permitAll()//
				.antMatchers("/swagger**/**").permitAll()
				.antMatchers("/*/*/noauth/**").permitAll()//
				.antMatchers("/*/authsec/**").permitAll()//
				.antMatchers("/*/*/authsec/**").permitAll()//
				.antMatchers("/*/*/favicon.ico/**").permitAll()
				
				
				
				
				.antMatchers("/*/platforms/**").permitAll()//
				.antMatchers("/*/*/platforms/**").permitAll()//
				.antMatchers("/*/platform/**").permitAll()//
				.antMatchers("/*/*/platform/**").permitAll()//
				.antMatchers("/*/api/**").permitAll()//
				.antMatchers("/*/*/api/**").permitAll()//
				.antMatchers("/*/sysdic/**").permitAll()//
				.antMatchers("/*/tenant/**").permitAll()//
				.antMatchers("/*/tenants/**").permitAll()//
				.antMatchers("/uaa/logout").permitAll()
				.anyRequest().authenticated()//
				.and()//
				.logout().logoutUrl("/uaa/logout").logoutSuccessUrl("/")//
				.clearAuthentication(true).deleteCookies("JSESSIONID")//
				.invalidateHttpSession(true).permitAll()
				.and()
				.csrf().disable(); 
//				.csrf().requireCsrfProtectionMatcher(csrfRequestMatcher()).csrfTokenRepository(this.getCSRFTokenRepository())
//				.and()
//				.addFilterAfter(this.createCSRFHeaderFilter(), CsrfFilter.class);
//		http.antMatcher("/**").authorizeRequests()
//				.antMatchers("/webjars/**", "/", "/**/*.html", "/assets/**", "/css/**", "/js/**", "/index.html", "/home.html", "/login.html")
//				.permitAll()//
//				.antMatchers("/*/webjars/**").permitAll()//
//				.antMatchers("/*/swagger-resources/**").permitAll()//
//				.antMatchers("/*/v2/**").permitAll()//
//				.antMatchers("/*/noauth/**").permitAll()//
//				.antMatchers("/*/swagger-ui.html/**").permitAll()//
////				.antMatchers("/*/swagger-ui.html#").permitAll()//
//				.antMatchers("/*/swagger-ui.html#/**").permitAll()//
//				.antMatchers("/*/*/noauth/**").permitAll()//
//				.antMatchers("/*/authsec/**").permitAll()//
//				.antMatchers("/*/*/authsec/**").permitAll()//
//				.antMatchers("/*/platforms/**").permitAll()//
//				.antMatchers("/*/*/platforms/**").permitAll()//
//				.antMatchers("/*/platform/**").permitAll()//
//				.antMatchers("/*/*/platform/**").permitAll()//
//				.antMatchers("/*/api/**").permitAll()//
//				.antMatchers("/*/*/api/**").permitAll()//
//				.antMatchers("/*/sysdic/**").permitAll()//
//				.antMatchers("/*/tenant/**").permitAll()//
//				.antMatchers("/*/tenants/**").permitAll()//
//				.anyRequest().authenticated()//
//				.and()//
////				.csrf().disable();
//				.csrf()
//				.csrfTokenRepository(this.getCSRFTokenRepository())//
//				.and()//
//				.addFilterAfter(this.createCSRFHeaderFilter(), CsrfFilter.class);
	}
//	private RequestMatcher csrfRequestMatcher() {
//		return new RequestMatcher() {
//			// Always allow the HTTP GET method
//			private final Pattern allowedMethods = Pattern.compile("^(GET|HEAD|OPTIONS|TRACE)$");
//
//			// Disable CSFR protection on the following urls:
//			private final AntPathRequestMatcher[] requestMatchers = { new AntPathRequestMatcher("/uaa/**") };
//
//			@Override
//			public boolean matches(HttpServletRequest request) {
//				if (allowedMethods.matcher(request.getMethod()).matches()) {
//					return false;
//				}
//
//				for (AntPathRequestMatcher matcher : requestMatchers) {
//					if (matcher.matches(request)) {
//						return false;
//					}
//				}
//				return true;
//			}
//		};
//	}

	/**
	 * Spring security offers in-built protection for cross site request forgery
	 * (CSRF) by needing a custom token in the header for any requests that are
	 * NOT safe i.e. modify the resources from the server e.g. POST, PUT & PATCH
	 * etc.<br>
	 * <br>
	 *
	 * This protection is achieved using cookies that send a custom value (would
	 * remain same for the session) in the first request and then the front-end
	 * would send back the value as a custom header.<br>
	 * <br>
	 *
	 * In this method we create a filter that is applied to the web security as
	 * follows:
	 * <ol>
	 * <li>Spring security provides the CSRF token value as a request attribute;
	 * so we extract it from there.</li>
	 * <li>If we have the token, Angular wants the cookie name to be
	 * "XSRF-TOKEN". So we add the cookie if it's not there and set the path for
	 * the cookie to be "/" which is root. In more complicated cases, this might
	 * have to be the context root of the api gateway.</li>
	 * <li>We forward the request to the next filter in the chain</li>
	 * </ol>
	 *
	 * The request-to-cookie filter that we add needs to be after the
	 * <code>csrf()</code> filter so that the request attribute for CsrfToken
	 * has been already added before we start to process it.
	 *
	 * @return
	 */
//	private Filter createCSRFHeaderFilter() {
//		return new OncePerRequestFilter() {
//			@Override
//			protected void doFilterInternal(HttpServletRequest request,
//											HttpServletResponse response, FilterChain filterChain)
//					throws ServletException, IOException {
//				String tempCSRFName = CsrfToken.class
//						.getName();
//				log.info("CSRF Name is: {}", tempCSRFName);
//				CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
//						.getName());
//				if (csrf != null) {
//					Cookie cookie = WebUtils.getCookie(request, CSRF_COOKIE_NAME);
//					String token = csrf.getToken();
//					if (cookie == null || token != null
//							&& !token.equals(cookie.getValue())) {
//						cookie = new Cookie(CSRF_COOKIE_NAME, token);
//						cookie.setPath("/");
//						response.addCookie(cookie);
//					}
//				}
//				log.info("The request Cookies are: {}", request.getCookies());
//				filterChain.doFilter(request, response);
//			}
//		};
//	}

	/**
	 * Angular sends the CSRF token in a custom header named "X-XSRF-TOKEN"
	 * rather than the default "X-CSRF-TOKEN" that Spring security expects.
	 * Hence we are now telling Spring security to expect the token in the
	 * "X-XSRF-TOKEN" header.<br><br>
	 *
	 * This customization is added to the <code>csrf()</code> filter.
	 *
	 * @return
	 */
//	private CsrfTokenRepository getCSRFTokenRepository() {
//		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
//		repository.setHeaderName(CSRF_ANGULAR_HEADER_NAME);
//		return repository;
//	}

}
