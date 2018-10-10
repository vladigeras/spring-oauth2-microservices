package ru.vladigeras.authorization.model;

import javax.persistence.*;

/**
 * @author vladi_geras on 09/10/2018
 */
@MappedSuperclass
public class LongIdentifiableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
