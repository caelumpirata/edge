package com.edge.application.views.Interface;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.ElementTable;
import com.edge.application.views.Table.TagTable;
import com.edge.application.views.Table.userTable;

public interface TagInterface  extends JpaRepository<TagTable, Long>{

	
	@Query(value="select * from tags order by tag_id asc",nativeQuery = true)
	public List<TagTable> tag_id_list();
	
	@Query(value="SELECT * FROM tags where id= :id",nativeQuery=true)
	public List<TagTable> tag_id_list(long id);
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from tags c where c.tag_id= :id",nativeQuery=true)
	public boolean check_tag_id(String id);
	
	@Query(value = "select * from tags order by tag_id asc",nativeQuery = true)
	public List<TagTable> get_grid();
	

		

	
	
	

}
