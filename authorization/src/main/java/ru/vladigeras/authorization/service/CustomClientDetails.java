package ru.vladigeras.authorization.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import ru.vladigeras.authorization.model.ClientEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author vladi_geras on 25/10/2018
 */
public class CustomClientDetails implements ClientDetails {

	private Long id;
	private String clientId;
	private String clientSecret;
	private String scope;
	private Set<String> resources;
	private Set<String> grantTypes;
	private Set<String> redirectUrls;
	private Integer accessTokenValiditySeconds;
	private Integer refreshTokenValiditySeconds;

	public CustomClientDetails(ClientEntity clientEntity) {
		this.id = clientEntity.getId();
		this.clientId = clientEntity.getClientId();
		this.clientSecret = clientEntity.getClientSecret();
		this.scope = clientEntity.getScope();
		this.resources = clientEntity.getResources().stream().map(Enum::name).collect(Collectors.toSet());
		this.grantTypes = clientEntity.getGrantTypes().stream().map(grantType -> grantType.name().toLowerCase()).collect(Collectors.toSet());
		this.redirectUrls = clientEntity.getRedirectUrls();
		this.accessTokenValiditySeconds = clientEntity.getAccessTokenValiditySeconds();
		this.refreshTokenValiditySeconds = clientEntity.getRefreshTokenValiditySeconds();
	}

	@Override
	public String getClientId() {
		return clientId;
	}

	@Override
	public Set<String> getResourceIds() {
		return resources;
	}

	@Override
	public boolean isSecretRequired() {
		return true;
	}

	@Override
	public String getClientSecret() {
		return clientSecret;
	}

	@Override
	public boolean isScoped() {
		return false;
	}

	@Override
	public Set<String> getScope() {
		return Set.of(scope);
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		return grantTypes;
	}

	@Override
	public Set<String> getRegisteredRedirectUri() {
		return redirectUrls;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}

	@Override
	public Integer getAccessTokenValiditySeconds() {
		return accessTokenValiditySeconds;
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return refreshTokenValiditySeconds;
	}

	@Override
	public boolean isAutoApprove(String scope) {
		return true;
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		return null;
	}
}
