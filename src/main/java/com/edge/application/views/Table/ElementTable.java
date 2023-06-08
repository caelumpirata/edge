package com.edge.application.views.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="element")
public class ElementTable {
	
	long id;
	String element_id;
	String element_name;
	String modbus_pointType;
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
	
	
	public String getModbus_pointType() {
		return modbus_pointType;
	}
	public void setModbus_pointType(String point_type) {
		this.modbus_pointType = point_type;
	}
	
	public String getRowid() {
		return rowid;
	}
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	

}
