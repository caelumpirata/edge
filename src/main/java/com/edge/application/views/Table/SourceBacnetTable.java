package com.edge.application.views.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="source_bacnet")
public class SourceBacnetTable {
	
	long id;
	
	String source_id;
	String port;
	String server_name;
	String broadcast_ip;
	String retrie;
	String timeout;
	String cov;
	
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
	
	
	public String getPort() {
		return port;
	}
	public void setPort(String p) {
		this.port = p;
	}
	
	
	
	public String getBroadcast_ip() {
		return broadcast_ip;
	}
	public void setBroadcast_ip(String url) {
		this.broadcast_ip = url;
	}
	
	public String getServer_name() {
		return server_name;
	}
	public void setServer_name(String s) {
		this.server_name = s;
	}
	
	public String getRetrie() {
		return retrie;
	}
	public void setRetrie(String policy) {
		this.retrie = policy;
	}
	
	public String getTimeout() {
		return timeout;
	}
	public void setTimeout(String set) {
		this.timeout = set;
	}
	
	public String getCov() {
		return cov;
	}
	public void setCov(String user) {
		this.cov = user;
	}
	
	
	public String getRowid() {
		return rowid;
	}
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	

}
