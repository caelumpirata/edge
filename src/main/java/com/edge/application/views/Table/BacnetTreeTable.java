package com.edge.application.views.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="bacnettree")
public class BacnetTreeTable {
	
	long id;
	long parent;
	//long tree_id;
	String device_id;
	String icon;
	String source;
	String broadcast_ip;
	String text;
	
	String type;
	String service;
	String server_name;
	
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
	
//	public long getTree_id() {
//		return tree_id;
//	}
//	public void setTree_id(long id) {
//		this.tree_id = id;
//	}
		
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String id) {
		this.device_id = id;
	}
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String ic) {
		this.icon = ic;
	}
	
	public String getSource() {
		return source;
	}
	public void setSource(String p) {
		this.source = p;
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
	
	public String getText() {
		return text;
	}
	public void setText(String t) {
		this.text = t;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String set) {
		this.type = set;
	}
	
	public String getService() {
		return service;
	}
	public void setService(String user) {
		this.service = user;
	}
	
	
	
	

}
