package com.edge.application.views.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="users")
public class userTable {
	
	long id;
	String user_id;
	String user_name;
	String password;
	String pt_password;
	String imstall_date;
	String modifide_date;
	String rowid;
	String tri;
	String numtries;
	String status;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String source_id) {
		this.user_id = source_id;
	}
	public String getuser_name() {
		return user_name;
	}
	public void setuser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getpassword() {
		return password;
	}
	public void setpassword(String password) {
		this.password = password;
	}
	public String getpt_password() {
		return pt_password;
	}
	public void setpt_password(String pt_password) {
		this.pt_password = pt_password;
	}
	
	public String getmx_tri() {
		return tri;
	}
	public void setmx_tri(String tri) {
		this.tri = tri;
	}
	public String getNumtries() {
		return numtries;
	}
	public void setNumtries(String numtries) {
		this.numtries = numtries;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String Status) {
		this.status = Status;
	}
	public String getImstall_date() {
		return imstall_date;
	}
	public void setImstall_date(String imstall_date) {
		this.imstall_date = imstall_date;
	}
	public String getModifide_date() {
		return modifide_date;
	}
	public void setModifide_date(String modifide_date) {
		this.modifide_date = modifide_date;
	}
	public String getRowid() {
		return rowid;
	}
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	

}
