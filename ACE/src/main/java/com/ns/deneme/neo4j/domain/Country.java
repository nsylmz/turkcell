package com.ns.deneme.neo4j.domain;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Country extends AbstractEntity {

    @Indexed(unique=true) 
    String code;
    
    String name;

    public Country(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Country() {
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