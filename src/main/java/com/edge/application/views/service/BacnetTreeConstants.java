package com.edge.application.views.service;

import java.io.*;
import java.lang.*;
import java.net.*;
import java.security.*;
import java.sql.*;
import java.text.*;
import java.util.*;

import java.util.Date;
import com.edge.application.views.Interface.peUtil;

public class BacnetTreeConstants {
	
	 public static String
	   _classNamePSQL	="org.postgresql.Driver",
		//_dbURLPSQL  = "jdbc:postgresql://mydatabase.ct9lh7rafpaw.ap-southeast-1.rds.amazonaws.com:5432/TagBrowser",
	    //_userNamePSQL	="testuser",
	   //_passwordPSQL	="silcore12345",
	   
	   _dbURLPSQL  = "jdbc:postgresql://localhost:5433/edge",
	    _userNamePSQL	="postgres",
	   _passwordPSQL	="sunil",
		_classNameMSQL	="com.mysql.jdbc.Driver";
		
		
		
	public static synchronized  int save(String query  )	{
	int inserted = 0;
	try	{
	Connection con = null;
	Class.forName(BacnetTreeConstants._classNamePSQL); 
	con = DriverManager.getConnection(BacnetTreeConstants._dbURLPSQL, BacnetTreeConstants._userNamePSQL, BacnetTreeConstants._passwordPSQL);
	Statement stmt	=	null;
	stmt 		= 	con.createStatement();
	inserted	=	stmt.executeUpdate(query);
	stmt.close();
	con.close();
	}
	catch(Exception e)	{
		System.out.println( e );
		return inserted;
	}
	return inserted;
}	
		
public static  Long id(String text,String source)	{
	
	Long id=null;
	try	{
		
	Connection con = null;
	ResultSet res =null;
	Class.forName(BacnetTreeConstants._classNamePSQL); 
	con = DriverManager.getConnection(BacnetTreeConstants._dbURLPSQL, BacnetTreeConstants._userNamePSQL, BacnetTreeConstants._passwordPSQL);
	Statement stmt	=	null;
	stmt 		= 	con.createStatement();
	 res = stmt.executeQuery("SELECT id FROM public.bacnettree where text='"+text+"'  and source='"+source+"'");

	 if(res!=null){
	    while(res.next())	{
	    	id	=	peUtil.obj2long(res.getString("id"));
			
	    }
	 }
	 res.close();
	stmt.close();
	con.close();
	}
	catch(Exception e)	{
		System.out.println( e );
		
	}
	return id;
}
	public static  Long parent_id(String text,Long root_id)	{
	
	Long id=null;
	try	{
		
	Connection con = null;
	ResultSet res =null;
	Class.forName(BacnetTreeConstants._classNamePSQL); 
	con = DriverManager.getConnection(BacnetTreeConstants._dbURLPSQL, BacnetTreeConstants._userNamePSQL, BacnetTreeConstants._passwordPSQL);
	Statement stmt	=	null;
	stmt 		= 	con.createStatement();
	 res = stmt.executeQuery("SELECT parent FROM public.bacnettree where text='"+text+"' and root_id='"+root_id+"'");

	 if(res!=null){
	    while(res.next())	{
	    	id	=	peUtil.obj2long(res.getString("parent"));
			
	    }
	 }
	 res.close();
	stmt.close();
	con.close();
	}
	catch(Exception e)	{
		System.out.println( e );
		
	}
	return id;
}

public static  void setkey()	{
	
	int inserted = 0;
	try	{
	Connection con = null;
	Class.forName(BacnetTreeConstants._classNamePSQL); 
	con = DriverManager.getConnection(BacnetTreeConstants._dbURLPSQL, BacnetTreeConstants._userNamePSQL, BacnetTreeConstants._passwordPSQL);
	Statement stmt	=	null;
	stmt 		= 	con.createStatement();
	inserted	=	stmt.executeUpdate("drop table public.bacnettree");
	
	con.close();
	}
	catch(Exception e)	{
		System.out.println( e );
		
	}
	
}
public static  void setStatuskey()	{
	
	int inserted = 0;
	try	{
	Connection con = null;
	Class.forName(BacnetTreeConstants._classNamePSQL); 
	con = DriverManager.getConnection(BacnetTreeConstants._dbURLPSQL, BacnetTreeConstants._userNamePSQL, BacnetTreeConstants._passwordPSQL);
	Statement stmt	=	null;
	stmt 		= 	con.createStatement();
	inserted	=	stmt.executeUpdate("drop table public.bacnetcovstatus");
	
	con.close();
	}
	catch(Exception e)	{
		System.out.println( e );
		
	}
	
}
public static  void createStatusTable()	{
	
	int inserted = 0;
	try	{
	Connection con = null;
	Class.forName(BacnetTreeConstants._classNamePSQL); 
	con = DriverManager.getConnection(BacnetTreeConstants._dbURLPSQL, BacnetTreeConstants._userNamePSQL, BacnetTreeConstants._passwordPSQL);
	Statement stmt	=	null;
	stmt 		= 	con.createStatement();
	inserted	=	stmt.executeUpdate("CREATE TABLE IF NOT EXISTS public.bacnetcovstatus(obj_name text,device_id text,server_name text, broadcast_ip text,status text)");

	
	con.close();
	}
	catch(Exception e)	{
		System.out.println( e );
		
	}
	
}
	
public static  void createTable()	{
	
	int inserted = 0;
	try	{
	Connection con = null;
	Class.forName(BacnetTreeConstants._classNamePSQL); 
	con = DriverManager.getConnection(BacnetTreeConstants._dbURLPSQL, BacnetTreeConstants._userNamePSQL, BacnetTreeConstants._passwordPSQL);
	Statement stmt	=	null;
	stmt 		= 	con.createStatement();
	inserted	=	stmt.executeUpdate("CREATE TABLE IF NOT EXISTS public.bacnettree(id bigint,icon text,text text,source text, server_name text,parent bigint,type text, service text,device_id text,broadcast_ip text)");

	
	con.close();
	}
	catch(Exception e)	{
		System.out.println( e );
		
	}
	
}
	
	
public static  String getSource(String text)	{
	
	
	String str="";
	try	{
		
	Connection con = null;
	ResultSet res =null;
	Class.forName(BacnetTreeConstants._classNamePSQL); 
	con = DriverManager.getConnection(BacnetTreeConstants._dbURLPSQL, BacnetTreeConstants._userNamePSQL, BacnetTreeConstants._passwordPSQL);
	Statement stmt	=	null;
	stmt 		= 	con.createStatement();
	 res = stmt.executeQuery("SELECT source FROM public.bacnettree where text='"+text+"'");

	 if(res!=null){
	    if(res.next())	{
	    	str	=	peUtil.obj2str(res.getString("source"));
			
	    }
	 }
	 res.close();
	stmt.close();
	con.close();
	}
	catch(Exception e)	{
		System.out.println( e );
		
	}
	return str;
}
	
public static  String  getService(String source)	{
	
	
	String str="";
	try	{
		
	Connection con = null;
	ResultSet res =null;
	Class.forName(BacnetTreeConstants._classNamePSQL); 
	con = DriverManager.getConnection(BacnetTreeConstants._dbURLPSQL, BacnetTreeConstants._userNamePSQL, BacnetTreeConstants._passwordPSQL);
	Statement stmt	=	null;
	stmt 		= 	con.createStatement();
	 res = stmt.executeQuery("SELECT service FROM public.bacnettree where source='"+source+"'");

	 if(res!=null){
	    if(res.next())	{
	    	str	=	peUtil.obj2str(res.getString("service"));
			
	    }
	 }
	 res.close();
	stmt.close();
	con.close();
	}
	catch(Exception e)	{
		System.out.println( e );
		
	}
	return str;
}	
	
	public static  Vector getAllSource(String device_id,String broadcast_ip,String server_name)	{
	
	Vector vc = new Vector();
	String str="";
	try	{
		
	Connection con = null;
	ResultSet res =null;
	Class.forName(BacnetTreeConstants._classNamePSQL); 
	con = DriverManager.getConnection(BacnetTreeConstants._dbURLPSQL, BacnetTreeConstants._userNamePSQL, BacnetTreeConstants._passwordPSQL);
	Statement stmt	=	null;
	stmt 		= 	con.createStatement();
	 res = stmt.executeQuery("SELECT source FROM public.bacnettree where device_id='"+device_id+"' and broadcast_ip='"+broadcast_ip+"' and server_name='"+server_name+"'");

	 if(res!=null){
	    while(res.next())	{
	    	str	=	peUtil.obj2str(res.getString("source"));
			if(!peUtil.isNullString(str)){
				vc.add(""+str);
			}
	    }
	 }
	 res.close();
	stmt.close();
	con.close();
	}
	catch(Exception e)	{
		System.out.println( e );
		
	}
	return vc;
}
public static  Vector getRemoteDevice(String broadcast_ip)	{
	
	Vector vc = new Vector();
	String str="";
	try	{
		
	Connection con = null;
	ResultSet res =null;
	Class.forName(BacnetTreeConstants._classNamePSQL); 
	con = DriverManager.getConnection(BacnetTreeConstants._dbURLPSQL, BacnetTreeConstants._userNamePSQL, BacnetTreeConstants._passwordPSQL);
	Statement stmt	=	null;
	stmt 		= 	con.createStatement();
	 res = stmt.executeQuery("SELECT distinct device_id FROM bacnetbackuptopic where broadcast_ip='"+broadcast_ip+"'");

	 if(res!=null){
	    while(res.next())	{
	    	str	=	peUtil.obj2str(res.getString("device_id"));
			if(!peUtil.isNullString(str)){
				vc.add(""+str);
			}
	    }
	 }
	 res.close();
	stmt.close();
	con.close();
	}
	catch(Exception e)	{
		System.out.println( e );
		
	}
	return vc;
}
public static  Vector getCOVTags(String device_id,String broadcast_ip,String server_name)	{
	
	Vector vc = new Vector();
	String str="";
	try	{
		
	Connection con = null;
	ResultSet res =null;
	Class.forName(BacnetTreeConstants._classNamePSQL); 
	con = DriverManager.getConnection(BacnetTreeConstants._dbURLPSQL, BacnetTreeConstants._userNamePSQL, BacnetTreeConstants._passwordPSQL);
	Statement stmt	=	null;
	stmt 		= 	con.createStatement();
	 res = stmt.executeQuery("SELECT obj_name FROM public.bacnetcovstatus where device_id='"+device_id+"' and broadcast_ip='"+broadcast_ip+"' and server_name='"+server_name+"'");

	 if(res!=null){
	    while(res.next())	{
	    	str	=	peUtil.obj2str(res.getString("obj_name"));
			if(!peUtil.isNullString(str)){
				vc.add(""+str);
			}
	    }
	 }
	 res.close();
	stmt.close();
	con.close();
	}
	catch(Exception e)	{
		System.out.println( e );
		
	}
	return vc;
}


public static  Vector getAvilableSource()	{
	
	Vector vc = new Vector();
	String str="";
	try	{
		
	Connection con = null;
	ResultSet res =null;
	Class.forName(BacnetTreeConstants._classNamePSQL); 
	con = DriverManager.getConnection(BacnetTreeConstants._dbURLPSQL, BacnetTreeConstants._userNamePSQL, BacnetTreeConstants._passwordPSQL);
	Statement stmt	=	null;
	stmt 		= 	con.createStatement();
	 res = stmt.executeQuery("SELECT distinct t1.source FROM public.bacnettree t1,public.tag_editor_bacnet t2 where t1.source=t2.source");

	 if(res!=null){
	    while(res.next())	{
	    	str	=	peUtil.obj2str(res.getString("source"));
			if(!peUtil.isNullString(str)){
				vc.add(""+str);
			}
	    }
	 }
	 res.close();
	stmt.close();
	con.close();
	}
	catch(Exception e)	{
		System.out.println( e );
		
	}
	return vc;
}
public static  Vector getAllSources() {

			Vector vc = new Vector();
			String str="";
			try {

			Connection con = null;
			ResultSet res =null;
			Class.forName(BacnetTreeConstants._classNamePSQL);
			con = DriverManager.getConnection(BacnetTreeConstants._dbURLPSQL, BacnetTreeConstants._userNamePSQL, BacnetTreeConstants._passwordPSQL);
			Statement stmt = null;
			stmt = con.createStatement();
			//res = stmt.executeQuery("SELECT distinct source FROM bacnettree where source IS NOT NULL and source !='1001'");
			res = stmt.executeQuery("SELECT distinct source FROM bacnettree where source IS NOT NULL");

			if(res!=null){
			   while(res.next()) {
				str = peUtil.obj2str(res.getString("source"));
			if(!peUtil.isNullString(str)){
			vc.add(""+str);
			}
			   }
			}
			res.close();
			stmt.close();
			con.close();
			}
			catch(Exception e) {
			System.out.println( e );

			}
			return vc;
}
	
	public static  String  getTopicName(String source)	{
	
	
	String str="";
	try	{
		
	Connection con = null;
	ResultSet res =null;
	Class.forName(BacnetTreeConstants._classNamePSQL); 
	con = DriverManager.getConnection(BacnetTreeConstants._dbURLPSQL, BacnetTreeConstants._userNamePSQL, BacnetTreeConstants._passwordPSQL);
	Statement stmt	=	null;
	stmt 		= 	con.createStatement();
	 res = stmt.executeQuery("SELECT topic FROM public.tag_editor_bacnet where source='"+source+"'");

	 if(res!=null){
	    if(res.next())	{
	    	str	=	peUtil.obj2str(res.getString("topic"));
			
	    }
	 }
	 res.close();
	stmt.close();
	con.close();
	}
	catch(Exception e)	{
		System.out.println( e );
		
	}
	return str;
}	
public static  List  getBacknetTopic()	{
	
	
	List<String> list=new ArrayList<String>();
	String str="";
	try	{
		
	Connection con = null;
	ResultSet res =null;
	Class.forName(BacnetTreeConstants._classNamePSQL); 
	con = DriverManager.getConnection(BacnetTreeConstants._dbURLPSQL, BacnetTreeConstants._userNamePSQL, BacnetTreeConstants._passwordPSQL);
	Statement stmt	=	null;
	stmt 		= 	con.createStatement();
	 res = stmt.executeQuery("SELECT topic FROM public.tag_editor_bacnet");

	 if(res!=null){
	    while(res.next())	{
	    	str	=	peUtil.obj2str(res.getString("topic"));
			list.add(str+".bacnet");
			
	    }
	 }
	 res.close();
	stmt.close();
	con.close();
	}
	catch(Exception e)	{
		System.out.println( e );
		
	}
	return list;
}		
public static  String getsource_topic(String topic)	{
	
	
	String str="";
	try	{
		
	Connection con = null;
	ResultSet res =null;
	Class.forName(BacnetTreeConstants._classNamePSQL); 
	con = DriverManager.getConnection(BacnetTreeConstants._dbURLPSQL, BacnetTreeConstants._userNamePSQL, BacnetTreeConstants._passwordPSQL);
	Statement stmt	=	null;
	stmt 		= 	con.createStatement();
	 res = stmt.executeQuery("SELECT source FROM public.tag_editor_bacnet where topic='"+topic+"'");

	 if(res!=null){
	    if(res.next())	{
	    	str	=	peUtil.obj2str(res.getString("source"));
			
	    }
	 }
	 res.close();
	stmt.close();
	con.close();
	}
	catch(Exception e)	{
		System.out.println( e );
		
	}
	return str;
}
public static  String getDevice_id(String topic)	{
	
	
	String str="";
	try	{
		
	Connection con = null;
	ResultSet res =null;
	Class.forName(BacnetTreeConstants._classNamePSQL); 
	con = DriverManager.getConnection(BacnetTreeConstants._dbURLPSQL, BacnetTreeConstants._userNamePSQL, BacnetTreeConstants._passwordPSQL);
	Statement stmt	=	null;
	stmt 		= 	con.createStatement();
	 res = stmt.executeQuery("SELECT devicename FROM public.tag_editor_bacnet where topic='"+topic+"'");

	 if(res!=null){
	    if(res.next())	{
	    	String 	str_device=	peUtil.obj2str(res.getString("devicename"));
			String id_val[]=str_device.split(" ");
			str= peUtil.obj2str(id_val[1]);
			
	    }
	 }
	 res.close();
	stmt.close();
	con.close();
	}
	catch(Exception e)	{
		System.out.println( e );
		
	}
	return str;
}

public static  String getBroadcastIp()	{
	
	
	String str="";
	try	{
		
	Connection con = null;
	ResultSet res =null;
	Class.forName(BacnetTreeConstants._classNamePSQL); 
	con = DriverManager.getConnection(BacnetTreeConstants._dbURLPSQL, BacnetTreeConstants._userNamePSQL, BacnetTreeConstants._passwordPSQL);
	Statement stmt	=	null;
	stmt 		= 	con.createStatement();
	 res = stmt.executeQuery("SELECT distinct broadcast_ip FROM public.backnet ");

	 if(res!=null){
	    if(res.next())	{
	    	str	=	peUtil.obj2str(res.getString("broadcast_ip"));
			
	    }
	 }
	 res.close();
	stmt.close();
	con.close();
	}
	catch(Exception e)	{
		System.out.println( e );
		
	}
	return str.trim();
}
public static  Vector getServerNames(String broadcast_ip) {

			Vector vc = new Vector();
			String str="";
			try {

			Connection con = null;
			ResultSet res =null;
			Class.forName(BacnetTreeConstants._classNamePSQL);
			con = DriverManager.getConnection(BacnetTreeConstants._dbURLPSQL, BacnetTreeConstants._userNamePSQL, BacnetTreeConstants._passwordPSQL);
			Statement stmt = null;
			stmt = con.createStatement();
			//res = stmt.executeQuery("SELECT distinct source FROM bacnettree where source IS NOT NULL and source !='1001'");
			res = stmt.executeQuery("SELECT distinct server_name FROM \"backnetServer\" where broadcast_ip='"+broadcast_ip+"'");

			if(res!=null){
			   while(res.next()) {
				str = peUtil.obj2str(res.getString("server_name"));
			if(!peUtil.isNullString(str)){
			vc.add(""+str);
			}
			   }
			}
			res.close();
			stmt.close();
			con.close();
			}
			catch(Exception e) {
			System.out.println( e );

			}
			return vc;
}
public static String check_server(String server_name,String broadcast_ip)
{
	String check="";
	try	{
		
	Connection con = null;
	ResultSet res =null;
	Class.forName(BacnetTreeConstants._classNamePSQL); 
	con = DriverManager.getConnection(BacnetTreeConstants._dbURLPSQL, BacnetTreeConstants._userNamePSQL, BacnetTreeConstants._passwordPSQL);
	Statement stmt	=	null;
	stmt 		= 	con.createStatement();
	 res = stmt.executeQuery("select CASE WHEN COUNT(c) > 0 THEN 'true' ELSE 'false' END from bacnettree c where c.server_name='"+server_name+"' and c.broadcast_ip='"+broadcast_ip+"'");

	 if(res!=null){
	    while(res.next())	{
	    	check	=	peUtil.obj2str(res.getString("case"));
			
	    }
	 }
	 res.close();
	stmt.close();
	con.close();
	}
	catch(Exception e)	{
		System.out.println( e );
		
	}
	return check;
}
}

