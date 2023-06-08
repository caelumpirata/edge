package com.edge.application.views.Interface;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.ElementTable;
import com.edge.application.views.Table.userTable;

public interface ElementInterface  extends JpaRepository<ElementTable, Long>{

	
	@Query(value="select * from element order by element_id asc",nativeQuery = true)
	public List<ElementTable> element_id_list();
	
	@Query(value="SELECT * FROM element where id= :id",nativeQuery=true)
	public List<ElementTable> element_id_list(long id);
	
	@Query(value="SELECT element_name FROM element where element_id= :id",nativeQuery=true)
	public String element_name(String id);
	
	@Query(value="SELECT id FROM element where element_id= :id",nativeQuery=true)
	public String element_main_id(String id);
	
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from element c where c.element_id= :id",nativeQuery=true)
	public boolean check_element_id(String id);
		
	@Transactional
	@Modifying
	@Query(value="UPDATE users SET  password=:password,pt_password= :pt_password WHERE  id= :id",nativeQuery=true)
	public void getupdatepassword(long id,String password,String pt_password);
	
	
	

}
