package com.edge.application.views.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="elementtagmapping")
public class ElementTagTable {
	
	long id;
	String element_id;
	String element_name;
	String tag_id;
	String tag_name;
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
	public String getTag_id() {
		return tag_id;
	}
	public void setTag_id(String id) {
		this.tag_id = id;
	}
	public String getTag_name() {
		return tag_name;
	}
	public void setTag_name(String name) {
		this.tag_name = name;
	}
	
		
	public String getRowid() {
		return rowid;
	}
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	

}
