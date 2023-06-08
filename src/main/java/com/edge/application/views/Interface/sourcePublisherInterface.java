package com.edge.application.views.Interface;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import com.edge.application.views.Table.sourcePublisherTable;

public interface sourcePublisherInterface  extends JpaRepository<sourcePublisherTable, Long>{

	
	@Query(value="select * from source_publisher order by source_id asc",nativeQuery = true)
	public List<sourcePublisherTable> source_id();
	
	@Query(value="SELECT * FROM source_publisher where id= :id",nativeQuery=true)
	public List<sourcePublisherTable> source_id_get(String id);
	
	@Query(value="SELECT * FROM source_publisher where source_id= :id",nativeQuery=true)
	public List<sourcePublisherTable> list_source_id(String id);
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from source_publisher c where c.source_name= :name",nativeQuery=true)
	public boolean check_source(String name);
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from source_publisher c where c.source_name= :name and c.source_id= :id",nativeQuery=true)
	public boolean check_source_id_name(String name,String id);
	
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from source_publisher c where  c.source_id= :id",nativeQuery=true)
	public boolean check_source_id_only(String id);
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from source_publisher c where  c.source_id= :id",nativeQuery=true)
	public boolean check_Source_id_publisher(String id);
	
	@Query(value="SELECT id FROM source_publisher where source_id= :id",nativeQuery=true)
	public long get_main_id(String id);
	
	@Query(value="SELECT publisher_type FROM source_publisher where source_id= :id",nativeQuery=true)
	public String get_PublisherType(String id);
	
	@Query(value="SELECT source_name FROM source_publisher where id= :id",nativeQuery=true)
	public String getSource_name(long id);
	
	@Query(value="SELECT source_name FROM source where source_id= :id",nativeQuery=true)
	public String getSource_name(String id);
	
	@Query(value="SELECT source_id FROM source_publisher where id= :id",nativeQuery=true)
	public String getSource_id(long id);
	
	@Query(value="SELECT publisher_type FROM source_publisher where source_id= :id",nativeQuery=true)
	public String getPublisher_type(String id);
	
	@Query(value="SELECT publisher_id FROM source_publisher where source_id= :id",nativeQuery=true)
	public String getPublisher_id(String id);
	
	@Transactional
	@Modifying
	@Query(value="delete FROM source_publisher where source_id= :id",nativeQuery=true)
	public int deleteRecord(String id);

	
	
	


}
