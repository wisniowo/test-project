package com.wis.demo.model;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Brand extends AbstractModel {

	public Brand() {
	}

	public Brand(Long id) {
		super.setId(id);
	}
	
	public Brand(String name) {
		this.name = name;
	}

	@NotEmpty
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
