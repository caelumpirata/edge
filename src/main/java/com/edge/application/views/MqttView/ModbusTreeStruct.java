package com.edge.application.views.MqttView;

public class ModbusTreeStruct {
	
	
	String id;
	String text;
	//String icon;
	String parent;
	String type;
	String device_id;
	
	
	
	
	public ModbusTreeStruct(String id, String text, String parent, String type,String device_id) {
		super();
		this.id = id;
		this.text = text;
		//this.icon = icon;
		this.parent = parent;
		this.type = type;
		this.device_id=device_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	/*public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}*/
	public String getparent() {
		return parent;
	}
	public void setparent(String parent) {
		this.parent = parent;
	}
	public String gettype() {
		return type;
	}
	public void settype(String type) {
		this.type = type;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	
	
	

}
