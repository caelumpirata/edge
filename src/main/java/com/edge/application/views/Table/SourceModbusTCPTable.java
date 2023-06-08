package com.edge.application.views.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="source_modbus_tcp")
public class SourceModbusTCPTable {
	
	long id;
	
	String source_id;
	String source_name;
	String port;
	String ip;
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
	public void setSource_id(String id) {
		this.source_id = id;
	}
	public String getSource_name() {
		return source_name;
	}
	public void setSource_name(String name) {
		this.source_name = name;
	}
	
	
	public String getPort() {
		return port;
	}
	public void setPort(String p) {
		this.port = p;
	}
	
	
	
	public String getIP() {
		return ip;
	}
	public void setIP(String data) {
		this.ip = data;
	}
	
	
		
	public String getRowid() {
		return rowid;
	}
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	

}
