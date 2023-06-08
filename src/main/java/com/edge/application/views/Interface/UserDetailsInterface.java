package com.edge.application.views.Interface;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.MqttConfigurationTable;
import com.edge.application.views.Table.UserDetailsTable;
import com.edge.application.views.Table.userTable;

public interface UserDetailsInterface  extends JpaRepository<UserDetailsTable, Long>{

	

	
	@Query(value="SELECT * FROM users where id= :id",nativeQuery=true)
	public List<UserDetailsTable> user_id_list(long id);
	
	@Query(value="select * from userdetails order by user_id asc",nativeQuery = true)
	public List<UserDetailsTable> user_permission_list();
	
	@Query(value="SELECT type FROM userdetails where user_id= :id",nativeQuery=true)
	public String get_user(String id);
	
	@Query(value="SELECT user_name FROM userdetails where id= :id",nativeQuery=true)
	public String get_user(long id);
	
	@Query(value="SELECT type FROM userdetails where id= :id",nativeQuery=true)
	public String get_type(long id);
	
	@Query(value="SELECT user_id FROM userdetails where id= :id",nativeQuery=true)
	public String get_user_id(long id);
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from userdetails c where c.user_id= :id",nativeQuery=true)
	public boolean check_user_id(String id);
	
	@Query(value="SELECT id FROM userdetails where user_id= :id",nativeQuery=true)
	public String get_id(String id);
	
	@Query(value="SELECT id FROM users where id= :id",nativeQuery=true)
	public String get_userid(String id);
	
	@Query(value="SELECT type FROM userdetails where user_id= :id",nativeQuery=true)
	public String usertype(String id);
	
	
}
