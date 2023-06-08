package com.edge.application.views.Interface;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.KafkaConfigurationTable;
import com.edge.application.views.Table.MqttConfigurationTable;
import com.edge.application.views.Table.SourceTable;

public interface KafkaInterface  extends JpaRepository<KafkaConfigurationTable, Long>{

	
	@Query(value="select * from kafkaconfiguration order by kafka_id asc",nativeQuery = true)
	public List<KafkaConfigurationTable> kafka_id();
	
	@Query(value="SELECT * FROM kafkaconfiguration where id= :id",nativeQuery=true)
	public List<KafkaConfigurationTable> id_list(long id);
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from kafkaconfiguration c where c.brokerip= :name",nativeQuery=true)
	public boolean check_broker(String name);
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from kafkaconfiguration c where c.brokerip= :broker and c.kafka_id=:id",nativeQuery=true)
	public boolean check_broker_kafka(String broker,String id);


}
