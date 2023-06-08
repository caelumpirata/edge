package com.edge.application.views.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;

@Entity
@Table(name="sourcePublisher")
public class sourcePublisherTable {
	
	long id;
	String source_id;
	String source_name;
	String publisher_type;
	String rowid;
	String publisher_id;
	
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
	public void setSource_id(String src_id) {
		this.source_id = src_id;
	}
	public String getSourceName() {
		return source_name;
	}
	public void setSourceName(String src_name) {
		this.source_name = src_name;
	}
	
	public String getPublisherType() {
		return publisher_type;
	}
	public void setPublisherType(String pub_type) {
		this.publisher_type = pub_type;
	}
	
	public String getPublisherId() {
		return publisher_id;
	}
	public void setPublisherId(String id) {
		this.publisher_id = id;
	}
	
	public String getRowid() {
		return rowid;
	}
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	

}
