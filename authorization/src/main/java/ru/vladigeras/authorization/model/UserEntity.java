package ru.vladigeras.authorization.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

/**
 * @author vladi_geras on 09/10/2018
 */
@Entity
@Table(name = "users")
public class UserEntity extends LongIdentifiableEntity {

	@Column(name = "login", unique = true, nullable = false)
	private String login;

	@Column(name = "phone_number", unique = true, nullable = false)
	private String phoneNumber;

	@JsonIgnore
	@Column(name = "password")
	private String password;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "middlename")
	private String middlename;

	@Column(name = "lastname")
	private String lastname;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "role")
	private Role role;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status")
	private UserStatus status;

	@OneToMany(mappedBy = "owner")
	private Set<ClientEntity> applications;

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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public Set<ClientEntity> getApplications() {
		return applications;
	}
}
