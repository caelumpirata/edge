package com.edge.application.views.Interface;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.BacnetLiveTable;

public interface BacnetLiveInterface extends JpaRepository<BacnetLiveTable, Long>{

	
	@Query(value="select * from bacnetlive where server_name= :server_name and source= :source",nativeQuery=true)
	public List<BacnetLiveTable> get_live(String server_name, String source);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE bacnetlive SET status='no' WHERE server_name= :ser_nm and source= :sou and tag_name= :tag",nativeQuery=true)
	public void update_status_live(String ser_nm,String sou,String tag);

}
