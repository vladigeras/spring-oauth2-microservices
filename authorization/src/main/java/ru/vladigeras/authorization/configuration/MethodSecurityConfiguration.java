package ru.vladigeras.authorization.configuration;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.expression.spel.support.StandardTypeLocator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.expression.OAuth2SecurityExpressionMethods;

/**
 * @author vladi_geras on 09/10/2018
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

	@Bean
	public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {

		return new DefaultMethodSecurityExpressionHandler() {

			@Override
			public StandardEvaluationContext createEvaluationContextInternal(final Authentication auth, final MethodInvocation mi) {

				var standardEvaluationContext = super.createEvaluationContextInternal(auth, mi);
				//TODO: префиксы не работают, чтобы можно было использовать @PreAuthorize("hasRole(T(Role).ROLE_MODERATOR") вместо полного пути класса
				((StandardTypeLocator) standardEvaluationContext.getTypeLocator()).registerImport("ru.vladigeras");
				standardEvaluationContext.setVariable("oauth2", new OAuth2SecurityExpressionMethods(auth));
				return standardEvaluationContext;
			}
		};
	}
}
