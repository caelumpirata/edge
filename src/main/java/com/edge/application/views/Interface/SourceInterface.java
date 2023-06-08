package com.edge.application.views.Interface;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.SourceTable;

public interface SourceInterface  extends JpaRepository<SourceTable, Long>{

	
	@Query(value="select * from source order by source_id asc",nativeQuery = true)
	public List<SourceTable> source_id();
	
	@Query(value="select * from source where protocol!='BACNET' order by source_id asc",nativeQuery = true)
	public List<SourceTable> source_id_next();
	
	@Query(value="SELECT * FROM source where id= :id",nativeQuery=true)
	public List<SourceTable> source_id_list(long id);
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from source c where c.source_name= :name",nativeQuery=true)
	public boolean check_source(String name);
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from source c where c.source_id= :id",nativeQuery=true)
	public boolean check_sourceID(String id);
	
	@Query(value="SELECT id FROM source where source_id= :id",nativeQuery=true)
	public long get_main_source_id(String id);

	@Transactional
	@Modifying
	@Query(value="UPDATE source SET protocol=:type where source_id= :id",nativeQuery=true)
	public void update_protocol(String id,String type);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE source SET source_id=:id where id= :type",nativeQuery=true)
	public void update_source_id(String id,long type);
	
	@Query(value="SELECT protocol FROM source where source_id= :id",nativeQuery=true)
	public String getprotocol(String id);

}
