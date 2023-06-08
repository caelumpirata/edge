package com.edge.application.views.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="mqtt_topic_tree")
public class MqttTopicTreeTable {
	
	long id;
	long parent;
	//long tree_id;
	String url;
	String port;
	String topic_name;
	String source_id;
	String source_name;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public long getParent() {
		return parent;
	}
	public void setParent(long id) {
		this.parent = id;
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
	
	
	
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	

	public String getTopic_name() {
		return topic_name;
	}
	public void setTopic_name(String url) {
		this.topic_name = url;
	}
	
	
	
	

}
