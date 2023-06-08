package com.edge.application.views.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;

@Entity
@Table(name="source_mqtt")
public class SourceMqttlTable {
	
	long id;
	String source_id;
	String source_name;
	String broker_ip;
	String port;
	String user;
	String password;
	String client_id;
	String qos;
	String keep_alive;
	String version;
	String session;
	String retaintion;
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
	public void setSource_id(String srcid) {
		this.source_id = srcid;
	}
	
	public String getSource_name() {
		return source_name;
	}
	public void setSource_name(String name) {
		this.source_name = name;
	}
	public String getBrokerIP() {
		return broker_ip;
	}
	public void setBrokerIP(String broker_ip) {
		this.broker_ip = broker_ip;
	}
	
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUser_name() {
		return user;
	}
	public void setUser_name(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getQos() {
		return qos;
	}
	public void setQos(String qos) {
		this.qos = qos;
	}
	public String getKeepAlive() {
		return keep_alive;
	}
	public void setKeepAlive(String keep_alive) {
		this.keep_alive = keep_alive;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	
	public String getRetaintion() {
		return retaintion;
	}
	public void setRetaintion(String retaintion) {
		this.retaintion = retaintion;
	}
	
	public String getRowid() {
		return rowid;
	}
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	

}
