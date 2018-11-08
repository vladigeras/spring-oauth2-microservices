package ru.vladigeras.authorization.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.cproject.mostransportauthorization.model.UserEntity;
import pro.cproject.mostransportauthorization.model.UserStatus;
import pro.cproject.mostransportauthorization.service.CprojectUserDetails;
import pro.cproject.mostransportauthorization.service.UserService;
import pro.cproject.mostransportauthorization.util.PhoneNumberUtil;

/**
 * @author vladi_geras on 09/10/2018
 */
@Service(value = "userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

		UserEntity userEntity;

		if (PhoneNumberUtil.isValid(login)) {
			userEntity = userService
					.findUserByPhoneNumber(login)
					.orElseThrow(() -> new UsernameNotFoundException("Пользователь с номером телефона " + login + " не существует"));
		} else {
			userEntity = userService
					.findUserByLogin(login)
					.orElseThrow(() -> new UsernameNotFoundException("Пользователь с логином " + login + " не существует"));
		}

		if (userEntity.getStatus().equals(UserStatus.BLOCKED))
			throw new UsernameNotFoundException("Ваш аккаунт был заблокирован");

		if (userEntity.getStatus().equals(UserStatus.NOT_ACTIVATED))
			throw new UsernameNotFoundException("Ваш аккаунт не активирован");

		return new CprojectUserDetails(userEntity);
	}
}
