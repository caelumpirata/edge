package com.edge.application.views.Interface;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.SourceOpcuaTable;



public interface SourceOpcuaInterface  extends JpaRepository<SourceOpcuaTable, Long>{

	
	@Query(value="select * from source_opcua order by source_id asc",nativeQuery = true)
	public List<SourceOpcuaTable> opcua_source_id();
	
	@Query(value = "select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from source_opcua c where c.source_id= :source_id",nativeQuery = true)
	public boolean check_source_opcua(String source_id);
	
	@Transactional
	@Modifying
	@Query(value="delete from source_opcua where source_id= :id",nativeQuery=true)
	public void deleteSource_opcua(String id);
	
	@Query(value = "select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from source_opcua c where c.port= :port",nativeQuery = true)
	public boolean check_source_opcua_port(String port);
	
	@Query(value = "select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from source_opcua c where c.end_point_url= :ip",nativeQuery = true)
	public boolean check_source_opcua_endpoint_url(String ip);
	
	@Query(value="SELECT * FROM source_opcua where source_id= :id",nativeQuery=true)
	public List<SourceOpcuaTable>  list_source_opcua(String id);

	@Query(value="SELECT end_point_url FROM source_opcua where source_id= :source_id and source_name= :source_name",nativeQuery=true)
	public String getEndpoint(String source_id, String source_name);
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from source_opcua c where c.source_id= :id",nativeQuery=true)
	public boolean check_sourceOPCUA(String id);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE source_opcua SET  end_point_url= :endpoint,port= :port,security_policy= :policy,security_setting= :setting WHERE  id= :id",nativeQuery=true)
	public void update_opc(String endpoint,String port,String policy,String setting,long id);
	
	@Query(value="SELECT end_point_url FROM source_opcua where source_id= :source_id",nativeQuery=true)
	public String geturl(String source_id);
}
