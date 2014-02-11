package com.ns.deneme.neo4j.domain;

import java.io.Serializable;
import java.util.Date;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@SuppressWarnings("serial")
@NodeEntity
public class MenuItem implements Serializable {
	
	@GraphId
	private Long id;
	
	@Indexed
	private String menuItemName;
	
//	@Fetch
//	@RelatedTo(type="menu", direction=Direction.OUTGOING)
//	private Menu menu;
	
	@Fetch
	@RelatedTo(type="menuItemPage", direction=Direction.OUTGOING)
	private Page menuItemPage;
	
	@Version
	private Long version;

	private Date transactionDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMenuItemName() {
		return menuItemName;
	}

	public void setMenuItemName(String menuItemName) {
		this.menuItemName = menuItemName;
	}

	public Page getMenuItemPage() {
		return menuItemPage;
	}

	public void setMenuItemPage(Page menuItemPage) {
		this.menuItemPage = menuItemPage;
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
