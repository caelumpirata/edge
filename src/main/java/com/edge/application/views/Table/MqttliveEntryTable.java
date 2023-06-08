package com.edge.application.views.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mqttlive_entry")
public class MqttliveEntryTable {
	
	long id;
	String source_id;
	String source_name;
	String topic_name;
	String status;
	String datetme;
	String broker;
	
	String topic_value;
	
	
	
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
	
	public String getTopic_name() {
		return topic_name;
	}
	public void setTopic_name(String name) {
		this.topic_name = name;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String st) {
		this.status = st;
	}
	
	public String getBroker() {
		return broker;
	}
	public void setBroker(String bk) {
		this.broker = bk;
	}
	
	public String getDatetime() {
		return datetme;
	}
	public void setDatetime(String dt) {
		this.datetme = dt;
	}
	
	public String getTopic_value() {
		return topic_value;
	}
	public void setTopic_value(String status) {
		this.topic_value = status;
	}
	
	

	
	
	

}
