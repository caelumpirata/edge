package com.edge.application.views.Interface;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.BacnetTagTable;
import com.edge.application.views.Table.MqttliveEntryTable;
import com.edge.application.views.Table.SourceTable;



public interface BacnetTagInterface  extends JpaRepository<BacnetTagTable, Long>{
	
	
	
	@Query(value="select * from bacnettag where device_id=:device_id order by device_id asc",nativeQuery = true)
	public List<BacnetTagTable> bacnet_tag_list(String device_id);

	@Query(value="select CASE WHEN COUNT(c) > 0 THEN 'true' ELSE 'false' END from bacnettag c where c.device_id=:id and c.server_name=:server_name and c.broadcast_ip=:broadacast_ip and c.obj_name=:obj_name and c.obj_id=:oid",nativeQuery=true)
	public boolean check_bacnet_obj(String id,String server_name,String broadacast_ip,String obj_name,String oid);
	


	
	
	

}
