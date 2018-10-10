package ru.vladigeras.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vladigeras.authorization.model.UserEntity;

import java.util.Optional;

/**
 * @author vladi_geras on 09/10/2018
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByLogin(String login);
}
