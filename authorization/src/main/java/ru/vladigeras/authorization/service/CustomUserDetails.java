package ru.vladigeras.authorization.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author vladi_geras on 09/10/2018
 */
public class CustomUserDetails implements UserDetails {

	private Long id;
	private String login;
	private String password;
	private Collection<? extends GrantedAuthority> roles;

	public CustomUserDetails(Long id, String login, String password,
							 Collection<? extends GrantedAuthority> roles) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Long getId() {
		return id;
	}
}
