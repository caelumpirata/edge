package com.edge.application.views.Interface;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.KafkaConfigurationTable;
import com.edge.application.views.Table.MqttConfigurationTable;
import com.edge.application.views.Table.SourceElementTable;
import com.edge.application.views.Table.SourceModbusRTUTable;
import com.edge.application.views.Table.SourceTable;

public interface SourceModbusRTUInterface  extends JpaRepository<SourceModbusRTUTable, Long>{

	
	@Query(value="select * from source_modbus_rtu order by source_id asc",nativeQuery = true)
	public List<SourceModbusRTUTable> mdbus_rtu_source_id();
	
	@Query(value = "select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from source_modbus_rtu c where c.source_id= :source_id",nativeQuery = true)
	public boolean check_source_modbust_rtu(String source_id);
	
	@Transactional
	@Modifying
	@Query(value="delete from source_modbus_rtu where source_id= :id",nativeQuery=true)
	public void deleteSource_modbus_rtu(String id);
	
	@Query(value = "select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from source_modbus_rtu c where c.com_port= :port",nativeQuery = true)
	public boolean check_source_comport(String port);
	
	@Query(value="SELECT * FROM source_modbus_rtu where source_id= :id",nativeQuery=true)
	public List<SourceModbusRTUTable>  list_source_modbust_rtu(String id);
}
