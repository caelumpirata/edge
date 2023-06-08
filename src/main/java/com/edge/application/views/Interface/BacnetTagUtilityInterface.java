package com.edge.application.views.Interface;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.BacnetTagUtilityTable;

public interface BacnetTagUtilityInterface extends JpaRepository<BacnetTagUtilityTable, Long>{

	
	@Query(value="SELECT * FROM bacnet_tag_utility where server_name= :url",nativeQuery=true)
	public List<BacnetTagUtilityTable> list_bacnet(String url);
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from bacnet_tag_utility c where c.device_id= :id and c.broadcast_ip= :ip and c.port= :port and c.server_name=:server_name",nativeQuery=true)
	public boolean check_bacnet_utility_source(String id,String ip,String port,String server_name);
	

	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from bacnet_tag_utility c where c.broadcast_ip= :ip and c.port= :port and c.topic=:topic and c.server_name=:server_name and c.obj_id=:obj_id and c.obj_name=:obj_name and c.device_id=:id ",nativeQuery=true)
	public boolean check_bacnet_utility(String ip,String port,String topic,String server_name,String obj_id,String obj_name,String id);
	
}
