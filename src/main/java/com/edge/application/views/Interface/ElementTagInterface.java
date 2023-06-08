package com.edge.application.views.Interface;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.ElementTable;
import com.edge.application.views.Table.ElementTagTable;
import com.edge.application.views.Table.TagTable;
import com.edge.application.views.Table.userTable;

public interface ElementTagInterface  extends JpaRepository<ElementTagTable, Long>{

	
	@Query(value="select * from elementtagmapping order by tag_id asc",nativeQuery = true)
	public List<ElementTagTable> element_tag_id_list();
	
	@Query(value="SELECT * FROM elementtagmapping where id= :id",nativeQuery=true)
	public List<ElementTagTable>  element_tag_id_list(long id);
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from elementtagmapping c where c.tag_id= :id",nativeQuery=true)
	public boolean check_element_tag_id(String id);
		
	@Transactional
	@Modifying
	@Query(value="delete FROM elementtagmapping where element_id= :id",nativeQuery=true)
	public void deleteMapElement(String id);
	
	
	

}
