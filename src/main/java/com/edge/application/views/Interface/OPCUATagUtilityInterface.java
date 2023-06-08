package com.edge.application.views.Interface;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.OPCUATagUtilityTable;

public interface OPCUATagUtilityInterface extends JpaRepository<OPCUATagUtilityTable, Long>{
	
	
	@Query(value="SELECT * FROM opcua_tag_utility where url= :url",nativeQuery=true)
	public List<OPCUATagUtilityTable> list_opcua(String url);
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from opcua_tag_utility c where c.nodeid= :nodeid and c.url= :url and c.topic=:topic",nativeQuery=true)
	public boolean check_opc_utility(String nodeid,String url,String topic);
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from opcua_tag_utility c where c.source_id= :id",nativeQuery=true)
	public boolean check_opc_utility_url(String id);

}
