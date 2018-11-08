package ru.vladigeras.authorization.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

/**
 * @author vladi_geras on 09/10/2018
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Value("${jwt.sign.key}")
	private String jwtSignKey;

	@Autowired
	@Qualifier("userDetailsServiceImpl")
	private UserDetailsService userDetailsService;

	@Autowired
	@Qualifier("clientDetailsServiceImpl")
	private ClientDetailsService clientDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		configurer.withClientDetails(clientDetailsService);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
		oauthServer
				.tokenKeyAccess("permitAll()")
				.checkTokenAccess("permitAll()")
				.allowFormAuthenticationForClients();
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer configurer) {
		var tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));

		configurer.tokenStore(tokenStore())
				.tokenEnhancer(tokenEnhancerChain)
				.authenticationManager(authenticationManager)
				.exceptionTranslator(loggingExceptionTranslator());
	}

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
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
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}

	@Bean
	public WebResponseExceptionTranslator<OAuth2Exception> loggingExceptionTranslator() {
		return new DefaultWebResponseExceptionTranslator() {
			@Override
			public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
				e.printStackTrace();

				var responseEntity = super.translate(e);
				HttpHeaders headers = new HttpHeaders();
				headers.setAll(responseEntity.getHeaders().toSingleValueMap());
				OAuth2Exception excBody = responseEntity.getBody();
				return new ResponseEntity<>(excBody, headers, responseEntity.getStatusCode());
			}
		};
	}
}

