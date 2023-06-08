package com.edge.application.views.Interface;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.subscribeLiveEntity;

public interface subscribeLiveRepo extends JpaRepository<subscribeLiveEntity, String> {
	
	@Transactional
	@Modifying
	@Query(value="DELETE FROM subscribe_live_entity WHERE endpoint= :new_url",nativeQuery=true)
	public void delete_url(String new_url);

}
