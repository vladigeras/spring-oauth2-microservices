package ru.vladigeras.service.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author vladi_geras on 09/10/2018
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private static final String SERVER_RESOURCE_ID = "SERVICE";

	@Value("${jwt.sign.key}")
	private String jwtSignKey;

	@Override
	public void configure(ResourceServerSecurityConfigurer config) {
		config.tokenServices(tokenServices()).resourceId(SERVER_RESOURCE_ID);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.httpBasic().disable()        //почему-то не отключает генерацию пароля
				.authorizeRequests()
				.antMatchers("/actuator/**",
						"/swagger-resources/**",
						"/swagger-ui.html",
						"/v2/api-docs",
						"/webjars/**").permitAll()
				.anyRequest().authenticated();

	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		var converter = new JwtAccessTokenConverter();
		converter.setSigningKey(jwtSignKey);
		return converter;
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		var defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		return defaultTokenServices;
	}
}
