package com.edge.application.views.Interface;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.KafkaConfigurationTable;
import com.edge.application.views.Table.MqttConfigurationTable;
import com.edge.application.views.Table.SourceMqttlTable;
import com.edge.application.views.Table.SourceOpcuaTable;



public interface SourceMqttInterface  extends JpaRepository<SourceMqttlTable, Long>{

	
	@Query(value="select * from source_mqtt order by source_id asc",nativeQuery = true)
	public List<SourceMqttlTable> mqtt_source_id();
	
	@Query(value = "select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from source_mqtt c where c.source_id= :source_id",nativeQuery = true)
	public boolean check_source_mqtt(String source_id);
	
	@Transactional
	@Modifying
	@Query(value="delete from source_mqtt where source_id= :id",nativeQuery=true)
	public void deleteSource_mqtt(String id);
	
	@Query(value = "select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from source_mqtt c where c.port= :port",nativeQuery = true)
	public boolean check_source_mqtt_port(String port);
	
	@Query(value = "select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from source_mqtt c where c.end_point_url= :ip",nativeQuery = true)
	public boolean check_source_mqtt_endpoint_url(String ip);
	
	@Query(value="SELECT * FROM source_mqtt where source_id= :id",nativeQuery=true)
	public List<SourceMqttlTable>  list_source_mqtt(String id);
	
	@Query(value="SELECT id FROM source_mqtt where source_id= :text1",nativeQuery=true)
	public long perent_id_source_mqtt(String text1);

	@Query(value="SELECT source_name FROM source_mqtt where source_id= :source_id",nativeQuery=true)
	public String getSource_name(String source_id);
	
	@Query(value="SELECT brokerip FROM source_mqtt where source_id= :source_id",nativeQuery=true)
	public String getBrokerip(String source_id);
	
	@Query(value="SELECT port FROM source_mqtt where source_id= :source_id",nativeQuery=true)
	public String getPort(String source_id);
}
