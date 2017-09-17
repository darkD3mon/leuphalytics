package de.leuphana.backend.data.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import de.leuphana.backend.data.AbstractEntity;

/**
 * 
 * @author Jonas Fährmann
 *
 */

@Entity
@Table(name = "ACCOUNT_ROLE")
public class AccountRole extends AbstractEntity {

	@NotNull
	@Column(name = "NAME")
	private String name;

	public AccountRole() {

	}

	public AccountRole(String name) {
		this.name = name;
	}

	// getRoleName because Grid in UserAdminView would not work
	// https://github.com/vaadin/framework/issues/9609
	public String getRoleName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}