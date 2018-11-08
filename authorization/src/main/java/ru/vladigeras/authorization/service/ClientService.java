package ru.vladigeras.authorization.service;

import ru.vladigeras.authorization.model.ClientEntity;

import java.util.Optional;

/**
 * @author vladi_geras on 25/10/2018
 */
public interface ClientService {

	Optional<ClientEntity> findClientByClientId(String clientId);

	ClientEntity get(Long id);
}
