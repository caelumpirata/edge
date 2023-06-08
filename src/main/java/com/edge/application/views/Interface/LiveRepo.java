package com.edge.application.views.Interface;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.LiveEntity;

public interface LiveRepo extends JpaRepository<LiveEntity, String> {

	@Query(value="SELECT * FROM public.live_entity where id= :id and endpoint= :url",nativeQuery = true)
	public LiveEntity selectData(String id,String url);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE live_entity SET endpoint= :new_url WHERE endpoint= :old_new",nativeQuery=true)
	public void update_url(String new_url,String old_new);
	
	@Transactional
	@Modifying
	@Query(value="DELETE FROM live_entity WHERE endpoint= :new_url",nativeQuery=true)
	public void delete_url(String new_url);
	
}
