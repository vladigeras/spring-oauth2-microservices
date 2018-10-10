package ru.vladigeras.authorization.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vladigeras.authorization.model.UserEntity;
import ru.vladigeras.authorization.repository.UserRepository;
import ru.vladigeras.authorization.service.UserService;

/**
 * @author vladi_geras on 09/10/2018
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional(readOnly = true)
	@Override
	public UserEntity findUserByLogin(String login) {
		return userRepository
				.findByLogin(login)
				.orElseThrow(() -> new RuntimeException("Пользователь " + login + " не существует"));
	}
}
