package com.edge.application.views.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rtu_tag_utility")
public class RTUTagUtilityTable {
	
	
	
	long id;
	String tagname;
//	String ref_add;
//	String reg_len;
//	String datatype;
//	String unit;
	String source_id;
	String topic;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTagname() {
		return tagname;
	}
	public void setTagname(String tagname) {
		this.tagname = tagname;
	}
//	public String getRef_add() {
//		return ref_add;
//	}
//	public void setRef_add(String ref_add) {
//		this.ref_add = ref_add;
//	}
//	public String getReg_len() {
//		return reg_len;
//	}
//	public void setReg_len(String reg_len) {
//		this.reg_len = reg_len;
//	}
//	public String getDatatype() {
//		return datatype;
//	}
//	public void setDatatype(String datatype) {
//		this.datatype = datatype;
//	}
//	public String getUnit() {
//		return unit;
//	}
//	public void setUnit(String unit) {
//		this.unit = unit;
//	}
	public String getSource_id() {
		return source_id;
	}
	public void setSource_id(String source_id) {
		this.source_id = source_id;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	

}
