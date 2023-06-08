package com.edge.application.views.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="LiveEntity")
public class LiveEntity
{
	Long mainID;
	
	String id;
	String name;
	String endpoint;
	String subscribeStatus;
	String srcTime;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getMainID() {
		return mainID;
	}

	public void setMainID(Long mainID) {
		this.mainID = mainID;
	}
	
	public String getSrcTime() {
		return srcTime;
	}

	public void setSrcTime(String srcTime) {
		this.srcTime = srcTime;
	}

	public LiveEntity() {}
	
	public String getSubscribeStatus() {
		return subscribeStatus;
	}

	public void setSubscribeStatus(String subscribeStatus) {
		this.subscribeStatus = subscribeStatus;
	}

	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	
	
}
