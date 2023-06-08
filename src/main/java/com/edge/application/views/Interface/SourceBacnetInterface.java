package com.edge.application.views.Interface;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.KafkaConfigurationTable;
import com.edge.application.views.Table.MqttConfigurationTable;
import com.edge.application.views.Table.SourceBacnetTable;
import com.edge.application.views.Table.SourceOpcuaTable;
import com.edge.application.views.Table.SourceTable;



public interface SourceBacnetInterface  extends JpaRepository<SourceBacnetTable, Long>{

	
	@Query(value="select * from source_bacnet order by source_id asc",nativeQuery = true)
	public List<SourceBacnetTable> bacnet_source_id();
	
	@Query(value="SELECT * FROM source_bacnet where source_id= :id",nativeQuery=true)
	public List<SourceBacnetTable> bacnet_id_list(String id);
	
	@Query(value="SELECT server_name FROM source_bacnet where source_id= :id",nativeQuery=true)
	public String bacnet_server_name(String id);
	
	@Query(value="SELECT source_name FROM source_bacnet where source_id= :id",nativeQuery=true)
	public String bacnet_source_name(String id);
	
	@Query(value="SELECT broadcast_ip FROM source_bacnet where source_id= :id and server_name=:name ",nativeQuery=true)
	public String bacnet_broadcast_ip(String id,String name);
	
	@Query(value = "select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from source_bacnet c where c.source_id= :source_id and broadcast_ip=:ip",nativeQuery = true)
	public boolean check_source_bacnet(String source_id,String ip);
	
	@Query(value = "select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from source_bacnet c where c.source_id= :source_id",nativeQuery = true)
	public boolean check_source_bacnet(String source_id);
	
	@Transactional
	@Modifying
	@Query(value="delete from source_bacnet where source_id= :id",nativeQuery=true)
	public void deleteSource_bacnet(String id);
	
		
	@Query(value="SELECT * FROM source_bacnet where source_id= :id",nativeQuery=true)
	public List<SourceBacnetTable>  list_source_bacnet(String id);

	@Query(value="SELECT broadcast_ip FROM source_bacnet where server_name= :name ",nativeQuery=true)
	public String get_broadcast_ip(String name);
	
	@Query(value="SELECT port FROM source_bacnet where server_name= :name ",nativeQuery=true)
	public String get_broadcast_port(String name);
}
