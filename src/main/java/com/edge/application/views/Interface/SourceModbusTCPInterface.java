package com.edge.application.views.Interface;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.KafkaConfigurationTable;
import com.edge.application.views.Table.MqttConfigurationTable;
import com.edge.application.views.Table.SourceElementTable;
import com.edge.application.views.Table.SourceModbusTCPTable;


public interface SourceModbusTCPInterface  extends JpaRepository<SourceModbusTCPTable, Long>{

	
	@Query(value="select * from source_modbus_tcp order by source_id asc",nativeQuery = true)
	public List<SourceModbusTCPTable> mdbus_tcp_source_id();
	
	@Query(value = "select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from source_modbus_tcp c where c.source_id= :source_id",nativeQuery = true)
	public boolean check_source_modbust_tcp(String source_id);
	
	@Transactional
	@Modifying
	@Query(value="delete from source_modbus_tcp where source_id= :id",nativeQuery=true)
	public void deleteSource_modbus_tcp(String id);
	
	@Query(value = "select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from source_modbus_tcp c where c.port= :port",nativeQuery = true)
	public boolean check_source_tcp_port(String port);
	
	@Query(value = "select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from source_modbus_tcp c where c.ip= :ip",nativeQuery = true)
	public boolean check_source_tcp_ip(String ip);
	
	@Query(value = "select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from source_modbus_tcp c where c.source_id= :id",nativeQuery = true)
	public boolean check_source_tcp(String id);
	
	@Query(value="SELECT * FROM source_modbus_tcp where source_id= :id",nativeQuery=true)
	public List<SourceModbusTCPTable>  list_source_modbust_tcp(String id);
}
