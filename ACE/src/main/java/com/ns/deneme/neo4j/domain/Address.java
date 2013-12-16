package com.ns.deneme.neo4j.domain;

import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Address extends AbstractEntity {

	private String street;
	
	private String city;

	private Country country;

	public Address(String street, String city, Country country) {
		this.street = street;
		this.city = city;
		this.country = country;
	}

	public Address() {

	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public Country getCountry() {
		return country;
	}
	
	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (getId() == null || obj == null
				|| !getClass().equals(obj.getClass())) {
			return false;
		}
		return getId().equals(((AbstractEntity) obj).getId());

	}

	@Override
	public int hashCode() {
		return getId() == null ? 0 : getId().hashCode();
	}
}