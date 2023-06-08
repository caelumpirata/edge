package com.edge.application.views.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="source_opcua")
public class SourceOpcuaTable {
	
	long id;
	
	String source_id;
	String source_name;
	String port;
	String end_point;
	String server_name;
	String security_policy;
	String security_setting;
	String username;
	String password;
	String certificate;
	String key;
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
	
	
	
	public String getEnd_point_url() {
		return end_point;
	}
	public void setEnd_point_url(String url) {
		this.end_point = url;
	}
	
	public String getServer_name() {
		return server_name;
	}
	public void setServer_name(String s) {
		this.server_name = s;
	}
	
	public String getSecurityPolicy() {
		return security_policy;
	}
	public void setSecurityPolicy(String policy) {
		this.security_policy = policy;
	}
	
	public String getSecuritySetting() {
		return security_setting;
	}
	public void setSecuritySetting(String set) {
		this.security_setting = set;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String user) {
		this.username = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String pass) {
		this.password = pass;
	}
	
	public String getCertificate() {
		return certificate;
	}
	public void setCertificate(String cert) {
		this.certificate = cert;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String ke) {
		this.key = ke;
	}
	public String getRowid() {
		return rowid;
	}
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	

}
