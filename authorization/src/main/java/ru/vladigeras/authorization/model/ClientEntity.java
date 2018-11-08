package ru.vladigeras.authorization.model;

import javax.persistence.*;
import java.util.Set;

/**
 * @author vladi_geras on 25/10/2018
 */
@Entity
@Table(name = "clients")
public class ClientEntity extends LongIdentifiableEntity {

	@Column(name = "client_id", unique = true, nullable = false)
	private String clientId;

	@Column(name = "client_secret")
	private String clientSecret;

	@Column(name = "application_title")
	private String title;

	@Column(name = "application_description")
	private String description;

	@Column(name = "contact_info")
	private String contactInfo;

	@Column(name = "scope")
	private String scope;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status")
	private ApplicationStatus status;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name = "clients_resources",
			joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
	)
	@Enumerated(value = EnumType.STRING)
	@Column(name = "resource")
	private Set<Resource> resources;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name = "clients_grant_types",
			joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
	)
	@Enumerated(value = EnumType.STRING)
	@Column(name = "grant_type")
	private Set<GrantType> grantTypes;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name = "clients_redirect_urls",
			joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
	)
	@Column(name = "redirect_url")
	private Set<String> redirectUrls;

	@Column(name = "access_token_validity_seconds")
	private Integer accessTokenValiditySeconds;

	@Column(name = "refresh_token_validity_seconds")
	private Integer refreshTokenValiditySeconds;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "owner_id", referencedColumnName = "id")
	private UserEntity owner;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	public Set<GrantType> getGrantTypes() {
		return grantTypes;
	}

	public void setGrantTypes(Set<GrantType> grantTypes) {
		this.grantTypes = grantTypes;
	}

	public Set<String> getRedirectUrls() {
		return redirectUrls;
	}

	public void setRedirectUrls(Set<String> redirectUrls) {
		this.redirectUrls = redirectUrls;
	}

	public Integer getAccessTokenValiditySeconds() {
		return accessTokenValiditySeconds;
	}

	public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
		this.accessTokenValiditySeconds = accessTokenValiditySeconds;
	}

	public Integer getRefreshTokenValiditySeconds() {
		return refreshTokenValiditySeconds;
	}

	public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
		this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
	}

	public UserEntity getOwner() {
		return owner;
	}

	public void setOwner(UserEntity owner) {
		this.owner = owner;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public ApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}
}
