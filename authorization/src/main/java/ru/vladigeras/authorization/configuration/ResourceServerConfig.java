package ru.vladigeras.authorization.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import ru.vladigeras.authorization.model.Resource;

/**
 * @author vladi_geras on 08/11/2018
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private static final String SERVER_RESOURCE_ID = Resource.AUTH.name();

	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;

	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;

	@Value("${jwt.sign.key}")
	private String jwtSignKey;

	@Autowired
	private DefaultTokenServices tokenServices;

	@Override
	public void configure(ResourceServerSecurityConfigurer config) {
		config
				.tokenServices(tokenServices)
				.resourceId(SERVER_RESOURCE_ID);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
				.and()
				.cors().disable()
				.csrf().disable()
				.authorizeRequests()
				.antMatchers(
						"/actuator/**",
						"/swagger-resources/**",
						"/swagger-ui.html",
						"/v2/api-docs",
						"/webjars/**",
						"/login**").permitAll()
				.anyRequest().authenticated()

				.and().formLogin().permitAll()
				.successHandler(authenticationSuccessHandler)
				.failureHandler(authenticationFailureHandler)

				.and().logout().permitAll()
				.logoutSuccessHandler(logoutSuccessHandler);
	}
}

