package com.edge.application.views.Interface;

import java.util.List;
import java.util.Vector;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.BacnetTreeTable;


public interface BacnetTreeInterface  extends JpaRepository<BacnetTreeTable, Long>{

	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN 'true' ELSE 'false' END from bacnettree c where c.server_name=:server_name and c.broadcast_ip=:broadacast_ip",nativeQuery=true)
	public boolean check_server(String server_name,String broadacast_ip);
	
	@Query(value="SELECT source FROM bacnettree where device_id=:device_id and broadcast_ip=:broadcast_ip and server_name=:server_name",nativeQuery=true)
	public Vector getAllSource(String device_id,String broadcast_ip,String server_name);
	
	@Query(value = "select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from bacnettree c where c.server_name= :server_name and broadcast_ip=:ip",nativeQuery = true)
	public boolean check_server_bacnet(String server_name,String ip);

	@Transactional
	@Modifying
	@Query(value="truncate table bacnettree",nativeQuery=true)
	public void truncate_bacnet_tree();
	
	@Transactional
	@Modifying
	@Query(value="delete from bacnettree where broadcast_ip=:ip and server_name=:server_name",nativeQuery=true)
	public void truncate_bacnet_server(String server_name , String ip);
	
	
	@Query(value="SELECT * FROM bacnettree where server_name= :server_name",nativeQuery=true)
	public List<BacnetTreeTable>  get_tree(String server_name);
	
	@Query(value="SELECT id FROM bacnettree where text=:text1",nativeQuery=true)
	public long perent_id(String text1);
	
	@Query(value="SELECT distinct server_name FROM bacnettree where device_id=:id",nativeQuery=true)
	public String get_server_name_bacnet(String id);

	
	
	

}
