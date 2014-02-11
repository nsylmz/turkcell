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
public class FieldAnnotation implements Serializable {

	@GraphId
	private Long id;

	@Indexed
	private String fieldAnnotationName;

	private String fieldAnnotationClass;

	private Set<FieldAnnotationMember> fieldAnnotationMemebers;

	@Version
	private Long version;

	private Date transactionDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFieldAnnotationName() {
		return fieldAnnotationName;
	}

	public void setFieldAnnotationName(String fieldAnnotationName) {
		this.fieldAnnotationName = fieldAnnotationName;
	}

	public String getFieldAnnotationClass() {
		return fieldAnnotationClass;
	}

	public void setFieldAnnotationClass(String fieldAnnotationClass) {
		this.fieldAnnotationClass = fieldAnnotationClass;
	}

	public Set<FieldAnnotationMember> getFieldAnnotationMemebers() {
		return fieldAnnotationMemebers;
	}

	public void setFieldAnnotationMemebers(
			Set<FieldAnnotationMember> fieldAnnotationMemebers) {
		this.fieldAnnotationMemebers = fieldAnnotationMemebers;
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

}
