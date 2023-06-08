package com.edge.application.views.Interface;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.ElementTable;
import com.edge.application.views.Table.ElementTagTable;
import com.edge.application.views.Table.SourceElementTable;
import com.edge.application.views.Table.TagTable;
import com.edge.application.views.Table.userTable;

public interface SourceElementInterface  extends JpaRepository<SourceElementTable, Long>{

	
	@Query(value="select * from sourceelementmapping order by tag_id asc",nativeQuery = true)
	public List<SourceElementTable> source_element_id_list();
	
	@Query(value="SELECT * FROM sourceelementmapping where id= :id",nativeQuery=true)
	public List<SourceElementTable>  source_element_id_list(long id);
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from sourceelementmapping c where c.element_id= :id and source_id= :sid",nativeQuery=true)
	public boolean check_source_element_id(String id,String sid);
	
	
		
	@Transactional
	@Modifying
	@Query(value="delete FROM sourceelementmapping where element_id= :id",nativeQuery=true)
	public void deleteMapSourceElement(String id);
	
	
	@Query(value="SELECT * FROM sourceelementmapping where source_id= :source_id and source_name= :source_name",nativeQuery=true)
	public List<SourceElementTable> get_source_element(String source_id,String source_name);
	
	
	

}
