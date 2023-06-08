package com.edge.application.views.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;

@Entity
@Table(name="kafkaconfiguration")
public class KafkaConfigurationTable {
	
	long id;
	String kafka_id;
	String broker_ip;
	String port;
	String user;
	String password;
	
	String rowid;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getKafka_id() {
		return kafka_id;
	}
	public void setKafka_id(String id) {
		this.kafka_id = id;
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
	
	
	public String getRowid() {
		return rowid;
	}
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	

}
