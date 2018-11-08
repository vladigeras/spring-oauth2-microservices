package ru.vladigeras.authorization.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.cproject.mostransportauthorization.model.BindingCardEntity;
import pro.cproject.mostransportauthorization.model.Role;
import pro.cproject.mostransportauthorization.model.UserEntity;
import pro.cproject.mostransportauthorization.model.UserStatus;
import pro.cproject.mostransportauthorization.model.dto.LoginDto;
import pro.cproject.mostransportauthorization.model.dto.PasswordDto;
import pro.cproject.mostransportauthorization.model.dto.UserDto;
import pro.cproject.mostransportauthorization.repository.BindingCardRepository;
import pro.cproject.mostransportauthorization.repository.UserRepository;
import pro.cproject.mostransportauthorization.service.OneTimePasswordService;
import pro.cproject.mostransportauthorization.service.SmsService;
import pro.cproject.mostransportauthorization.service.UserService;
import pro.cproject.mostransportauthorization.util.PasswordGenerator;
import pro.cproject.mostransportauthorization.util.PhoneNumberUtil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author vladi_geras on 09/10/2018
 */
@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Value("${sms.use}")
	private String useSms;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private OneTimePasswordService oneTimePasswordService;

	@Autowired
	private SmsService smsService;

	@Autowired
	private BindingCardRepository bindingCardRepository;

	@Transactional(readOnly = true)
	@Override
	public Optional<UserEntity> findUserByLogin(String login) {
		return userRepository.findByLogin(login);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<UserEntity> findUserByPhoneNumber(String phoneNumber) {
		return userRepository.findByPhoneNumber(phoneNumber);
	}

	@Transactional
	@Override
	public void registration(UserDto userDto) {
		var userEntity = new UserEntity();

		userEntity.setFirstname(userDto.firstname);
		userEntity.setMiddlename(userDto.middlename);
		userEntity.setLastname(userDto.lastname);
		userEntity.setRole(Role.ROLE_USER);
		userEntity.setPassword(passwordEncoder.encode(userDto.password));
		userEntity.setStatus(UserStatus.NOT_ACTIVATED);

		if (findUserByLogin(userDto.login).isPresent())
			throw new RuntimeException("Логин " + userDto.login + " занят");

		userEntity.setLogin(userDto.login);

		if (findUserByPhoneNumber(userDto.phoneNumber).isPresent())
			throw new RuntimeException("Номер " + userDto.phoneNumber + " уже привязан к другому аккаунту");

		userEntity.setPhoneNumber(userDto.phoneNumber);

		userRepository.save(userEntity);

		var confirmPassword = oneTimePasswordService.generatePassword(userEntity.getLogin());

		if (Boolean.valueOf(useSms)) {
			smsService.sendSms(confirmPassword, userEntity.getPhoneNumber());
		}

		LOGGER.info("Пользователь " + userDto.login + " был успешно зарегистрирован в системе");
		LOGGER.info("Пользователь " + userDto.login + " получил временный код подтверждения: " + confirmPassword);
	}

	@Transactional(readOnly = true)
	@Override
	public UserEntity get(Long userId) {
		return userRepository
				.findById(userId)
				.orElseThrow(() -> new RuntimeException("Пользователь с id = " + userId + " не найден"));
	}

	@Transactional
	@Override
	public void confirmRegistration(String userLogin, String code) {
		var userEntity = findUserByLogin(userLogin)
				.orElseThrow(() -> new RuntimeException("Пользователь с логином " + userLogin + " не существует"));

		if (!userEntity.getStatus().equals(UserStatus.NOT_ACTIVATED))
			throw new RuntimeException("Ваш аккаунт уже был активирован. Повторная активация не требуется");

		var password = oneTimePasswordService.getPassword(userLogin);

		if (!code.equals(password)) throw new RuntimeException("Неверный код подтверждения");

		oneTimePasswordService.clearPassword(userLogin);

		userEntity.setStatus(UserStatus.ACTIVATED);

		userRepository.save(userEntity);

		LOGGER.info("Пользователь " + userEntity.getLogin() + " успешно прошел подтверждение регистрации");
	}

	@Transactional
	@Override
	public void resendCode(String userLogin) {
		var userEntity = findUserByLogin(userLogin)
				.orElseThrow(() -> new RuntimeException("Пользователь с логином " + userLogin + " не существует"));

		if (!userEntity.getStatus().equals(UserStatus.NOT_ACTIVATED))
			throw new RuntimeException("Ваш аккаунт уже был активирован. Повторная активация не требуется");

		oneTimePasswordService.clearPassword(userLogin);

		var code = oneTimePasswordService.generatePassword(userLogin);

		if (Boolean.valueOf(useSms)) {
			smsService.sendSms(code, userEntity.getPhoneNumber());
		}

		LOGGER.info("Пользователь " + userEntity.getLogin() + " получил новый временный код подтверждения: " + code);
	}

	@Transactional
	@Override
	public void changePassword(Long userId, PasswordDto userCredentials) {
		var userEntity = get(userId);

		if (!passwordEncoder.matches(userCredentials.oldPassword, userEntity.getPassword()))
			throw new RuntimeException("Старый пароль неверен");

		userEntity.setPassword(passwordEncoder.encode(userCredentials.newPassword));

		userRepository.save(userEntity);

		LOGGER.info("Пользователь " + userEntity.getLogin() + " сменил пароль");
	}

	@Transactional(readOnly = true)
	@Override
	public Set<BindingCardEntity> getBankCardsByUserId(Long userId) {
		var userEntity = get(userId);

		return new HashSet<>(bindingCardRepository.getAllByUserAndDeletedTimeIsNull(userEntity));
	}

	@Transactional
	@Override
	public void resetPassword(LoginDto login) {
		UserEntity userEntity;

		if (PhoneNumberUtil.isValid(login.login)) {
			userEntity = findUserByPhoneNumber(login.login)
					.orElseThrow(() -> new RuntimeException("Пользователь с номером телефона " + login.login + " не существует"));
		} else {
			userEntity = findUserByLogin(login.login)
					.orElseThrow(() -> new RuntimeException("Пользователь с логином " + login.login + " не существует"));
		}

		var passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
				.useDigits(true)
				.useLower(true)
				.useUpper(true)
				.build();

		var newPassword = passwordGenerator.generate(8);

		userEntity.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(userEntity);

		if (Boolean.valueOf(useSms)) {
			smsService.sendSms(newPassword, userEntity.getPhoneNumber());
		}

		LOGGER.info("Пользователь " + userEntity.getLogin() + " сбросил и получил новый пароль");
	}
}
