package com.edge.application.views.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="source_modbus_rtu")
public class SourceModbusRTUTable {
	
	long id;
	
	String source_id;
	String source_name;
	String com_port;
	String baud;
	String data_bits;
	String stop_bits;
	String parity;
	String poll_interval;
	String rep_interval;
	String poll_set_time;
	String rep_set_time;
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
	
	
	public String getCom_port() {
		return com_port;
	}
	public void setCom_port(String port) {
		this.com_port = port;
	}
	
	
	
	public String getData_bits() {
		return data_bits;
	}
	public void setData_bits(String data) {
		this.data_bits = data;
	}
	
	public String getStop_bits() {
		return stop_bits;
	}
	public void setStop_bits(String stop) {
		this.stop_bits = stop;
	}
	
	public String getParity() {
		return parity;
	}
	public void setParity(String p) {
		this.parity = p;
	}
	
	public String getPoll_interval() {
		return poll_interval;
	}
	public void setPoll_interval(String poll) {
		this.poll_interval = poll;
	}
	
	public String getPolling_set_time() {
		return poll_set_time;
	}
	public void setPolling_set_time(String st) {
		this.poll_set_time = st;
	}
	public String getRep_interval() {
		return rep_interval;
	}
	public void setRep_interval(String rep) {
		this.rep_interval = rep;
	}
	public String getReporting_set_time() {
		return rep_set_time;
	}
	public void setReporting_set_time(String repSt) {
		this.rep_set_time = repSt;
	}
	
	public String getBaud() {
		return baud;
	}
	public void setBaud(String b) {
		this.baud = b;
	}
		
	public String getRowid() {
		return rowid;
	}
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	

}
