package ru.vladigeras.authorization.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vladigeras.authorization.service.CustomUserDetails;
import ru.vladigeras.authorization.service.UserService;

import java.util.Collections;

/**
 * @author vladi_geras on 09/10/2018
 */
@Service(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

		var userEntity = userService.findUserByLogin(login);

		return new CustomUserDetails(userEntity.getId(), userEntity.getLogin(), userEntity.getPassword(),
				Collections.singleton(new SimpleGrantedAuthority(userEntity.getRole().name()))
		);
	}
}
