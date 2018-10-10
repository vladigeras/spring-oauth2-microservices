package ru.vladigeras.authorization.model;

import javax.persistence.*;

/**
 * @author vladi_geras on 09/10/2018
 */
@Entity
@Table(name = "users")
public class UserEntity extends LongIdentifiableEntity {

	@Column(name = "login")
	private String login;

	@Column(name = "password")
	private String password;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "role")
	private Role role;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
