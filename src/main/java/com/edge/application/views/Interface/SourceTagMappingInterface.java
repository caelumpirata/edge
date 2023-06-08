package com.edge.application.views.Interface;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.SourceTagMappingTable;

public interface SourceTagMappingInterface extends JpaRepository<SourceTagMappingTable, Long>{
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN 'true' ELSE 'false' END from sourcetagmapping c where c.source_id=:id and c.source_name=:name and c.element_name=:elem and tag_name=:tag_name",nativeQuery=true)
	public boolean check_source_tag_element(String id,String name,String elem,String tag_name);
	
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN 'true' ELSE 'false' END from sourcetagmapping c where c.source_id=:id",nativeQuery=true)
	public boolean check_source_mapping_tagid(String id);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE sourcetagmapping SET  tag_name= :tag_name,ref_add= :add,reg_len= :len,datatype= :datatype,unit= :unit,element_name= :element_name,modbus_point= :modbus_point WHERE  id= :id",nativeQuery=true)
	public void update_sourcetagmapping(String tag_name,String add,String len,String datatype,String unit,String element_name,String modbus_point,long id);

	@Query(value="SELECT * FROM sourcetagmapping where source_id= :id order by ref_add asc",nativeQuery=true)
	public List<SourceTagMappingTable>  source_id_finalList(String id);
	
	
}
