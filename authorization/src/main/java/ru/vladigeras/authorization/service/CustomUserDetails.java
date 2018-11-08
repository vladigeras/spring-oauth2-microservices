package ru.vladigeras.authorization.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.vladigeras.authorization.model.UserEntity;
import ru.vladigeras.authorization.model.UserStatus;

import java.util.Collection;
import java.util.Collections;

/**
 * @author vladi_geras on 09/10/2018
 */
public class CustomUserDetails implements UserDetails {

	private Long id;
	private String login;
	private String password;
	private Collection<? extends GrantedAuthority> roles;
	private UserStatus status;

	public CustomUserDetails(UserEntity userEntity) {
		this.id = userEntity.getId();
		this.login = userEntity.getLogin();
		this.password = userEntity.getPassword();
		this.roles = Collections.singleton(new SimpleGrantedAuthority(userEntity.getRole().name()));
		this.status = userEntity.getStatus();
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
