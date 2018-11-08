package ru.vladigeras.authorization.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vladigeras.authorization.model.ApplicationStatus;
import ru.vladigeras.authorization.service.ClientService;
import ru.vladigeras.authorization.service.CustomClientDetails;

/**
 * @author vladi_geras on 25/10/2018
 */
@Service(value = "clientDetailsServiceImpl")
public class ClientDetailsServiceImpl implements ClientDetailsService {

	@Autowired
	private ClientService clientService;

	@Transactional(readOnly = true)
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		var clientEntity = clientService
				.findClientByClientId(clientId)
				.orElseThrow(() -> new ClientRegistrationException("Клиент с client_id = " + clientId + " не существует"));

		if (!clientEntity.getStatus().equals(ApplicationStatus.CONFIRMED)) {
			if (clientEntity.getStatus().equals(ApplicationStatus.BLOCKED))
				throw new RuntimeException("Приложение было заблокировано администратором");

			if (clientEntity.getStatus().equals(ApplicationStatus.DECLINED))
				throw new RuntimeException("Заявка на получение доступа данного приложения была отклонена");

			if (clientEntity.getStatus().equals(ApplicationStatus.NEW))
				throw new RuntimeException("Заявка еще не была рассмотрена");
		}

		return new CustomClientDetails(clientEntity);
	}
}
