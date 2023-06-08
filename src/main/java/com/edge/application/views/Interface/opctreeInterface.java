package com.edge.application.views.Interface;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.opctreetable;


public interface opctreeInterface extends JpaRepository<opctreetable, Long>{
	
	
	
	@Modifying
	@Transactional
	@Query(value = "drop table opctree",nativeQuery = true)
    void deletetable();
	
	@Transactional
	@Modifying
	@Query(value="CREATE TABLE IF NOT EXISTS opctree(id bigint NOT NULL,name character varying(255),node_class character varying(255),node_id character varying(255),parent bigint,text character varying(255),type character varying(255),CONSTRAINT opctree_pkey PRIMARY KEY (id))",nativeQuery = true)
	public void createTable();
	
	
	@Query(value="select * from opctree where url= :url",nativeQuery = true)
	public List<opctreetable> getTree(String url);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM opctree where url= :url",nativeQuery = true)
    void deletetableurl(String url);
	
	@Query(value="SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM opctree c WHERE c.url =:url",nativeQuery = true)
	public boolean  treedata(String url);

}
