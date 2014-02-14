package com.ns.deneme.neo4j.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@SuppressWarnings("serial")
@NodeEntity
public class App implements Serializable {
	
	@GraphId
	private Long id;
	
	@Indexed
	private String appName;
	
	private boolean approved;
	
	private String desc;
	
	@Fetch
	@RelatedTo(type="appMenu", direction=Direction.INCOMING)
	private Set<Menu> appMenus;
	
	@Version
	private Long version;
	
	private Date transactionDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Set<Menu> getAppMenus() {
		return appMenus;
	}

	public void setAppMenus(Set<Menu> appMenus) {
		this.appMenus = appMenus;
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
