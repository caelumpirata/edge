package com.edge.application.views.Interface;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.BacnetLiveTable;
import com.edge.application.views.Table.MqttliveEntryTable;

public interface MqttLiveInterEntryface extends JpaRepository<MqttliveEntryTable, Long>{

	
	@Query(value="select * from mqttlive_entry order by source_id",nativeQuery=true)
	public List<MqttliveEntryTable> get_mqtt_live_entry();
	
	@Query(value="SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM mqttlive_entry c WHERE c.source_id =:id",nativeQuery = true)
	public boolean  check_mqtt_live_entry(String id);
	
	@Transactional
	@Modifying
	@Query(value="delete from mqttlive_entry WHERE source_id= :id",nativeQuery=true)
	public void delete_mqtt_entry_live(String id);

}
