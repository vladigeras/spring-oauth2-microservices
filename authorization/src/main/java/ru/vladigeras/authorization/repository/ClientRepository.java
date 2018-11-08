package ru.vladigeras.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vladigeras.authorization.model.ClientEntity;

import java.util.Optional;

/**
 * @author vladi_geras on 25/10/2018
 */
@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

	Optional<ClientEntity> findByClientId(String clientId);
}
