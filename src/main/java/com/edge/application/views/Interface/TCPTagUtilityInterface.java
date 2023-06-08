package com.edge.application.views.Interface;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.TCPTagUtilityTable;

public interface TCPTagUtilityInterface extends JpaRepository<TCPTagUtilityTable, Long>{

	
	@Query(value="SELECT * FROM tcp_tag_utility where source_id= :url",nativeQuery=true)
	public List<TCPTagUtilityTable> list_tcp(String url);
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from tcp_tag_utility c where c.tagname= :tagname and c.source_id= :source_id",nativeQuery=true)
	public boolean check_tcp_utility(String tagname,String source_id);
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from tcp_tag_utility c where c.source_id= :source_id",nativeQuery=true)
	public boolean check_tcp_utility_src_id(String source_id);

}
