package com.edge.application.views.Interface;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.BacnetLiveTable;
import com.edge.application.views.Table.MqttLiveTable;

public interface MqttLiveInterface extends JpaRepository<MqttLiveTable, Long>{

	
	@Query(value="select * from mqttlive where source_id= :source_id",nativeQuery=true)
	public List<MqttLiveTable> get_live(String source_id);
	
	@Query(value = "select status from mqttlive where source_id= :id",nativeQuery = true)
	public String mqtt_topic_status(String id);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE mqttlive SET status='no' WHERE source_id= :source_id and topic_name= :topic",nativeQuery=true)
	public void update_status_live(String source_id,String topic);
	
	
	@Transactional
	@Modifying
	@Query(value="delete from mqttlive  WHERE source_id= :source_id and topic_name= :topic",nativeQuery=true)
	public void delete_live_mqtt_tree(String source_id,String topic);

}
