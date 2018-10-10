package ru.vladigeras.authorization.service;

import ru.vladigeras.authorization.model.UserEntity;

/**
 * @author vladi_geras on 09/10/2018
 */
public interface UserService {

	UserEntity findUserByLogin(String login);
}
