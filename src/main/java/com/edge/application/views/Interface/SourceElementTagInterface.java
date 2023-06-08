package com.edge.application.views.Interface;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.ElementTagTable;
import com.edge.application.views.Table.SourceElementTable;
import com.edge.application.views.Table.SourceElementTagTable;
import com.edge.application.views.Table.TagTable;

public interface SourceElementTagInterface extends JpaRepository<SourceElementTagTable, Long>{
	
	@Query(value = "select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from sourceelementtagmapping c where c.source_id= :source_id and c.tag_id= :tag_id and c.element_id=:elem_id",nativeQuery = true)
	public boolean check_tag_source(String source_id,String tag_id,String elem_id);
	
	@Transactional
	@Modifying
	@Query(value="delete FROM sourceelementtagmapping where element_id= :id",nativeQuery=true)
	public void deleteMapSourceElementTag(String id);
	
	@Transactional
	@Modifying
	@Query(value="delete FROM sourceelementtagmapping where source_id= :id",nativeQuery=true)
	public void deleteMap_SourceElementTag(String id);
	
	@Query(value="select * from sourceelementtagmapping order by tag_id asc",nativeQuery = true)
	public List<SourceElementTagTable> source_element_tag_id_list();
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from sourceelementtagmapping c where source_id= :sid",nativeQuery=true)
	public boolean check_source_tag_element_id_only(String sid);
	
}
