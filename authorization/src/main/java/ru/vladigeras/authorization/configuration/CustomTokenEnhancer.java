package ru.vladigeras.authorization.configuration;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import ru.vladigeras.authorization.service.CustomUserDetails;

import java.util.HashMap;

/**
 * @author vladi_geras on 09/10/2018
 */
public class CustomTokenEnhancer implements TokenEnhancer {

	private static final String USER_ID_FIELD_IN_TOKEN = "user_id";

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		var userDetails = (CustomUserDetails) authentication.getUserAuthentication().getPrincipal();

		var additionalInfo = new HashMap<String, Object>();

		additionalInfo.put(USER_ID_FIELD_IN_TOKEN, userDetails.getId());

		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
		return accessToken;
	}
}
