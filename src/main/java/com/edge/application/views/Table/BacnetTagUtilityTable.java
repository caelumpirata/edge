package com.edge.application.views.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="bacnet_tag_utility")
public class BacnetTagUtilityTable {
	
	long id;
	String obj_name;
	String device_id;
	String server_name;
	String broadcast_ip;
	String obj_id;
	String topic;
	String port;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getObj_name() {
		return obj_name;
	}
	public void setObj_name(String id) {
		this.obj_name = id;
	}
		
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String id) {
		this.device_id = id;
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
	
	public String getObj_id() {
		return obj_id;
	}
	public void setObj_id(String oid) {
		this.obj_id = oid;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
	
	
	
	
	

}
