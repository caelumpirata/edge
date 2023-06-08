package com.edge.application.views.Interface;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.MqttConfigurationTable;
import com.edge.application.views.Table.SourceTable;

public interface MqttInterface  extends JpaRepository<MqttConfigurationTable, Long>{

	
	@Query(value="select * from mqttconfiguration order by mqtt_id asc",nativeQuery = true)
	public List<MqttConfigurationTable> mqtt_id();
	
	@Query(value="SELECT * FROM mqttconfiguration where id= :id",nativeQuery=true)
	public List<MqttConfigurationTable> id_list(long id);
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from mqttconfiguration c where c.brokerip= :name",nativeQuery=true)
	public boolean check_broker(String name);
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from mqttconfiguration c where c.brokerip= :broker and c.mqtt_id= :id",nativeQuery=true)
	public boolean check_broker_surce(String broker,String id);


}
