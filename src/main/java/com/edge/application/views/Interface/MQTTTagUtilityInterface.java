package com.edge.application.views.Interface;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.MQTTTagUtilityTable;


public interface MQTTTagUtilityInterface extends JpaRepository<MQTTTagUtilityTable, Long>{

	
	
	@Query(value="SELECT * FROM mqtt_tag_utility where broker_ip= :url",nativeQuery=true)
	public List<MQTTTagUtilityTable> list_mqtt(String url);
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from mqtt_tag_utility c where c.source_id= :id",nativeQuery=true)
	public boolean check_mqtt_utility_source(String id);
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from mqtt_tag_utility c where c.tag= :tag and c.source_id= :source_id and c.topic=:topic and c.broker_ip=:broker and c.port=:port",nativeQuery=true)
	public boolean check_mqtt_utility(String tag,String source_id,String topic,String port,String broker);
}
