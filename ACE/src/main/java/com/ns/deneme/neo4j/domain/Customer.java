package com.ns.deneme.neo4j.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.util.Assert;

@NodeEntity
public class Customer extends AbstractEntity {

	private String firstName, lastName;

	@Indexed(unique = true)
	private String emailAddress;

	@RelatedTo(type = "ADDRESS", direction = Direction.BOTH)
	private Set<Address> addresses = new HashSet<Address>();

	public Customer(String firstName, String lastName, String emailAddress) {
		Assert.hasText(firstName);
		Assert.hasText(lastName);
		Assert.hasText(emailAddress);

		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
	}

	protected Customer() {

	}

	public void add(Address address) {
		this.addresses.add(address);
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Set<Address> getAddresses() {
		return Collections.unmodifiableSet(addresses);
	}

	public boolean hasAddress(Address address) {
		return addresses.contains(address);
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

	@Override
	public String toString() {
		return String.format("%s %s <%s>", firstName, lastName, emailAddress);
	}
}
