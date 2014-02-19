package com.ns.deneme.neo4j.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@SuppressWarnings("serial")
@NodeEntity
public class FieldAnnotationMember implements Serializable {

	@GraphId
	private Long id;

	@Indexed(unique = true)
	private String fieldAnnotationMemberName;

	private String fieldAnnotationMemberType;

	private String fieldAnnotationMemberValue;

	@Version
	private Long version;

	private Date transactionDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFieldAnnotationMemberName() {
		return fieldAnnotationMemberName;
	}

	public void setFieldAnnotationMemberName(String fieldAnnotationMemberName) {
		this.fieldAnnotationMemberName = fieldAnnotationMemberName;
	}

	public String getFieldAnnotationMemberType() {
		return fieldAnnotationMemberType;
	}

	public void setFieldAnnotationMemberType(String fieldAnnotationMemberType) {
		this.fieldAnnotationMemberType = fieldAnnotationMemberType;
	}

	public String getFieldAnnotationMemberValue() {
		return fieldAnnotationMemberValue;
	}

	public void setFieldAnnotationMemberValue(String fieldAnnotationMemberValue) {
		this.fieldAnnotationMemberValue = fieldAnnotationMemberValue;
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
