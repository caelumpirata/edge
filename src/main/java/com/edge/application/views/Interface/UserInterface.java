package com.edge.application.views.Interface;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.edge.application.views.Table.userTable;

public interface UserInterface  extends JpaRepository<userTable, Long>{

	
	@Query(value="select * from users order by user_id asc",nativeQuery = true)
	public List<userTable> user_id();
	
	@Query(value="SELECT * FROM users where id= :id",nativeQuery=true)
	public List<userTable> user_id_list(long id);
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from users c where c.user_name= :name",nativeQuery=true)
	public boolean check_user(String name);
	
	@Query(value="select * from users order by user_id asc",nativeQuery = true)
	public List<userTable> user_id_name();
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from userdetails c where c.id= :id",nativeQuery=true)
	public boolean check_user_id(String id);
	
	@Query(value="SELECT id FROM users where user_id= :id",nativeQuery=true)
	public String get_userid(String id);
	
	@Query(value="SELECT user_id FROM users where id= :id",nativeQuery=true)
	public String get_user_id(long id);
	
	@Query(value="select CASE WHEN COUNT(c) > 0 THEN true ELSE false END from users c where c.user_id= :id",nativeQuery=true)
	public boolean check_exist_id(String id);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE users SET  numtries='0',status= :status WHERE user_id= :user_id and id= :id",nativeQuery=true)
	public void update_user(long id,String user_id,String status);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE users SET  password=:password,pt_password= :pt_password WHERE  id= :id",nativeQuery=true)
	public void getupdatepassword(long id,String password,String pt_password);
	
	
	

}
