package com.github.mlaursen.brews.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.EqualsBuilder;

@MappedSuperclass
public abstract class GeneratedIdEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	/**
	 * Sets the Entity's id
	 * @param id the id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Gets the Entity's id
	 * @return the id
	 */
	public Long getId() {
	  return id;
	}
	
	@Override
	public boolean equals(Object obj) {
	  return EqualsBuilder.reflectionEquals(this, obj, false);
	}
}
