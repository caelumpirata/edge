package com.edge.application.views.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="sourceelementmapping")
public class SourceElementTable {
	
	long id;
	String element_id;
	String element_name;
	String source_id;
	String source_name;
	String rowid;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getElement_id() {
		return element_id;
	}
	public void setElement_id(String id) {
		this.element_id = id;
	}
	public String getElement_name() {
		return element_name;
	}
	public void setElement_name(String name) {
		this.element_name = name;
	}
	public String getSource_id() {
		return source_id;
	}
	public void setSource_id(String id) {
		this.source_id = id;
	}
	public String getSource_name() {
		return source_name;
	}
	public void setSource_name(String name) {
		this.source_name = name;
	}
	
		
	public String getRowid() {
		return rowid;
	}
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	

}
