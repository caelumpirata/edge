package com.edge.application.views.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mqttlive")
public class MqttLiveTable {
	
	long id;
	String source_id;
	String topic_name;
	String status;
	
	
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
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTopic_name() {
		return topic_name;
	}
	public void setTopic_name(String tag_name) {
		this.topic_name = tag_name;
	}

	
	
	

}
