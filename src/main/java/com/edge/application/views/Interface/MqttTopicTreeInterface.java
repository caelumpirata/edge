package com.edge.application.views.Interface;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import com.edge.application.views.Table.MqttTopicTreeTable;
import com.edge.application.views.Table.SourceOpcuaTable;

public interface MqttTopicTreeInterface  extends JpaRepository<MqttTopicTreeTable, Long>{

	
	@Query(value="select * from mqtt_topic_tree order by url",nativeQuery = true)
	public List<MqttTopicTreeTable> mqtt_topic_id();
	
	@Query(value = "select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from mqtt_topic_tree c where c.url= :url and c.port=:port and c.source_id=:id",nativeQuery = true)
	public boolean check_mqtt_url(String url,String port,String id);
	
	
	@Query(value = "select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from mqtt_topic_tree c where c.source_id= :id",nativeQuery = true)
	public boolean check_mqtt_tree_srcid(String id);
	
	@Query(value="SELECT * FROM mqtt_topic_tree where source_id= :id",nativeQuery=true)
	public List<MqttTopicTreeTable>  list_source_mqtt_tree(String id);
	
	@Transactional
	@Modifying
	@Query(value="delete from mqtt_topic_tree where url= :url and port=:port and source_id=:id",nativeQuery=true)
	public void deleteTopicTree_mqtt(String url,String port,String id);
	
	@Query(value = "select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from mqtt_topic_tree c where c.topic_name= :name and url= :url and port=:port and source_id=:id",nativeQuery = true)
	public boolean check_mqtt_topic(String name,String url,String port,String id);
	
	@Query(value="SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM mqtt_topic_tree c WHERE c.url =:url and c.port=:port",nativeQuery = true)
	public boolean  treedata(String url,String port);
	
	@Query(value="SELECT * FROM mqtt_topic_tree where source_name= :name",nativeQuery=true)
	public List<MqttTopicTreeTable> getlistname(String name);
	
	
}
