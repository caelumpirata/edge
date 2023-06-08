package com.edge.application.views.Interface;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.BacnetCOVStatusTable;



public interface BacnetCOVStatusInterface  extends JpaRepository<BacnetCOVStatusTable, Long>{

	@Query(value="select CASE WHEN COUNT(c) > 0 THEN 'true' ELSE 'false' END from bacnetcovstatus c where c.server_name=:server_name and c.broadcast_ip=:broadacast_ip and c.obj_name=:obj_name",nativeQuery=true)
	public String check_cov_obj(String server_name,String broadacast_ip,String obj_name);
	

	@Transactional
	@Modifying
	@Query(value="truncate table bacnetcovstatus",nativeQuery=true)
	public void truncate_bacnet_cov_status();

	
	
	

}
