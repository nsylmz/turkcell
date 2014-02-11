package com.ns.deneme.neo4j.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@SuppressWarnings("serial")
@NodeEntity
public class Field implements Serializable {

	@GraphId
	private Long id;

	@Indexed
	private String fieldName;

	private Set<FieldAnnotation> fieldAnnotations;

	@Version
	private Long version;

	private Date transactionDate;

	private boolean indexed;

	private String type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Set<FieldAnnotation> getFieldAnnotations() {
		return fieldAnnotations;
	}

	public void setFieldAnnotations(Set<FieldAnnotation> fieldAnnotations) {
		this.fieldAnnotations = fieldAnnotations;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public boolean isIndexed() {
		return indexed;
	}

	public void setIndexed(boolean indexed) {
		this.indexed = indexed;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
