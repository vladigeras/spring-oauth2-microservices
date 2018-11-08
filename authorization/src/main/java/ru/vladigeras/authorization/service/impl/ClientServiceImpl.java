package ru.vladigeras.authorization.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vladigeras.authorization.model.ClientEntity;
import ru.vladigeras.authorization.repository.ClientRepository;
import ru.vladigeras.authorization.service.ClientService;

import java.util.Optional;

/**
 * @author vladi_geras on 25/10/2018
 */
@Service
public class ClientServiceImpl implements ClientService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientServiceImpl.class);

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	@Override
	public Optional<ClientEntity> findClientByClientId(String clientId) {
		return clientRepository.findByClientId(clientId);
	}

	@Transactional(readOnly = true)
	@Override
	public ClientEntity get(Long id) {
		return clientRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Клиент с id = " + id + " не найден"));
	}
}
