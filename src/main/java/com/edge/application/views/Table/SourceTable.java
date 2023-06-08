package com.edge.application.views.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;

@Entity
@Table(name="source")
public class SourceTable {
	
	long id;
	String source_id;
	String source_name;
	String application_name;
	String protocol;
	String longtitude;
	String latitude;
	String location_name;
	String imstall_date;
	String modifide_date;
	String rowid;
	
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
	public String getApplication_name() {
		return application_name;
	}
	public void setApplication_name(String application_name) {
		this.application_name = application_name;
	}
	
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String proto) {
		this.protocol = proto;
	}
	public String getLongtitude() {
		return longtitude;
	}
	public void setLongtitude(String longtitude) {
		this.longtitude = longtitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLocation_name() {
		return location_name;
	}
	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}
	public String getImstall_date() {
		return imstall_date;
	}
	public void setImstall_date(String imstall_date) {
		this.imstall_date = imstall_date;
	}
	public String getModifide_date() {
		return modifide_date;
	}
	public void setModifide_date(String modifide_date) {
		this.modifide_date = modifide_date;
	}
	
	
	public String getRowid() {
		return rowid;
	}
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	

}
