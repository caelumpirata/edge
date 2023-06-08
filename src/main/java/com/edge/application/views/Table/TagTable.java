package com.edge.application.views.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="tags")
public class TagTable {
	
	long id;
	String tag_id;
	String tag_name;
	String tag_reg_address;
	String tag_ref_address;
	String data_type;
	String unit;
	String rowid;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getTagRegAddress() {
		return tag_reg_address;
	}
	public void setTagRegAddress(String add) {
		this.tag_reg_address = add;
	}
	public String getTagRefAddress() {
		return tag_ref_address;
	}
	public void setTagRefAddress(String add) {
		this.tag_ref_address = add;
	}
	
	public String getDataType() {
		return data_type;
	}
	public void setDataType(String add) {
		this.data_type = add;
	}
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String add) {
		this.unit = add;
	}
		
	public String getRowid() {
		return rowid;
	}
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	

}
