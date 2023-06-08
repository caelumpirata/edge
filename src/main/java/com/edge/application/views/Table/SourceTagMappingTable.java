package com.edge.application.views.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sourcetagmapping")
public class SourceTagMappingTable {
	
	long id;
	String source_id;
	String source_name;
	String element_name;
	String modbus_point;
	String tag_name;
	String ref_add;
	String reg_len;
	String datatype;
	String unit;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSource_id() {
		return source_id;
	}
	public void setSource_id(String source_id) {
		this.source_id = source_id;
	}
	public String getSource_name() {
		return source_name;
	}
	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}
	public String getElement_name() {
		return element_name;
	}
	public void setElement_name(String element_name) {
		this.element_name = element_name;
	}
	public String getModbus_point() {
		return modbus_point;
	}
	public void setModbus_point(String modbus_point) {
		this.modbus_point = modbus_point;
	}
	public String getTag_name() {
		return tag_name;
	}
	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}
	public String getRef_add() {
		return ref_add;
	}
	public void setRef_add(String ref_add) {
		this.ref_add = ref_add;
	}
	public String getReg_len() {
		return reg_len;
	}
	public void setReg_len(String reg_len) {
		this.reg_len = reg_len;
	}
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	

}
