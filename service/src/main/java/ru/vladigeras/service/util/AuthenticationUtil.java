package ru.vladigeras.service.util;

import org.springframework.boot.json.JsonParserFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.Optional;

/**
 * @author vladi_geras on 09/10/2018
 */
public class AuthenticationUtil {

	private AuthenticationUtil() {
	}

	public static final String USER_ID_FIELD_IN_TOKEN = "user_id";

	public static Optional<Long> getUserIdFromAuthentication(Authentication authentication) {
		if (authentication == null) return Optional.empty();

		var details = (OAuth2AuthenticationDetails) authentication.getDetails();
		var claims = JwtHelper.decode(details.getTokenValue()).getClaims();

		var values = JsonParserFactory.getJsonParser().parseMap(claims);
		return Optional.of(Long.valueOf(values.get(USER_ID_FIELD_IN_TOKEN).toString()));
	}
}
