package com.edge.application.views.form;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.edge.application.views.MainLayout;
import com.edge.application.views.Grid.UserList;
import com.edge.application.views.Interface.peUtil;
import com.edge.application.views.Table.UserDetailsTable;
import com.edge.application.views.Table.Validation;
import com.edge.application.views.Table.userTable;
import com.edge.application.views.service.EdgeService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("User Lock Unlock Form")
@Route(value = "userLockUnlockForm" ,layout = MainLayout.class)
public class userLockUnlockForm extends VerticalLayout implements HasUrlParameter<String>{
	
	public static final String ROUTE_NAME="userLockUnlockForm";
	
	TextField txt_user_id     = new TextField("User Id");
	ComboBox<String> user = new ComboBox<>("User Id-Name");
	ComboBox<String> status = new ComboBox<>("Status");
		
	Button btn_save = new Button("SAVE");
	Button btn_next = new Button("NEXT");
	
	HorizontalLayout btn_layout = new HorizontalLayout();
	FormLayout formLayout       = new FormLayout();
	
	Binder<Validation> binder  =new Binder<Validation>();
	Validation entity          =new Validation();
	List<String> ls=new ArrayList<String>();
	
	String getid="",final_id="";
	private int int_id = 0;
	private long id = 0;

	
	@Autowired
	private EdgeService service;
	
	long main_id;
	
	@SuppressWarnings("unused")
	//@PostConstruct
	public void init(String para)
	{
	    String global_user_id=""+para;
	    
	    System.out.println("Sunil-------"+global_user_id);
	   
	    status.setAllowCustomValue(true); 
	    status.setItems("Lock","Unlock");   
        // btn style
		btn_save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_next.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		
		// source id autoincrement code
		
		if(peUtil.isNullString(global_user_id)) {
		for (userTable sou_table : service.userType_list()) {

			String u_id = sou_table.getUser_id().toString();
			String u_name = sou_table.getuser_name().toString();
			String concnate = u_id+"-"+u_name;
			ls.add(concnate);
			
		}
		}else {
			String str = global_user_id+"-"+service.get_user_name(global_user_id);
			ls.add(str);
			txt_user_id.setValue(str);
		}

		user.setItems(ls);
	
		
		// Validation code

		
			binder.forField(user).asRequired("Select User Id-Name").bind(Validation::getString,Validation::setString);
		    binder.forField(status).asRequired("status").bind(Validation::getString,Validation::setString);

	
		
		
			
		
		// all click Event code
		btn_save.addClickListener(s->{
			if (binder.writeBeanIfValid(entity)) 
			{
				
				
					
						String user_id_name="";String user_id="",user_name="",user_type="",modi_date="",instal_date="";
						user_id_name =  user.getValue().toString();
						user_id=user_id_name.split("-")[0];
						user_name  =user_id_name.split("-")[01];
											
						user_type   		= status.getValue().toString();
						
						
						
						if(service.get_User_id(user_id))
		        		{
							
//							userTable st = new userTable();
//							st.setUser_id(user_id);
//							st.setuser_name(user_name);
//							st.setStatus(user_type);
//							
//							System.out.println("IDDDD______"+id);
//							st.setId(Integer.parseInt(id));
//							st.setNumtries(""+0);
							//service.user_save(st);
							try
							{
								String id =service.get_userid(user_id);
								service.getupdatestatus(Long.parseLong(id), user_id, user_type);
							}
							catch(Exception ex)
							{
								
							}
							
		        		}
						
						
						UI.getCurrent().navigate(UserList.class);
					
        		
				
				
				
			}// biner if condition
			else
			{
				Notification.show("Action Denied", 3000, Position.MIDDLE);
			}
			
		});
				
		
		 // layout add code
		btn_layout.add(btn_save, btn_next);
		formLayout.add( user, status, btn_layout );
		formLayout.setResponsiveSteps(
		        // Use one column by default
		        new ResponsiveStep("0", 1)
		        );
		add(formLayout);
		
		
	}
	
	
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
		
			init(parameter);
		
	}
	public static String genUniqueID()
	{ 
	  Date d = new Date();
	  long utn = d.getTime();

	  double rand = Math.random();

	  StringBuffer str = new StringBuffer();
	  str.append( utn + "-" + rand);

	  String id = md5(str.toString());

	  

	  return id;
	}
	public static String md5(String str)
{
MessageDigest md;
try
{
md = MessageDigest.getInstance("MD5");

md.update(str.getBytes());
byte[] b = md.digest();
String out = dumpBytes(b);
return out;
}
catch (Exception e) {

}

return "";
}
private static String dumpBytes(byte[] bytes)
{
int size = bytes.length;
StringBuffer sb = new StringBuffer(size * 2);

for (int i = 0; i < size; ++i) {
String s = Integer.toHexString(bytes[i]);

if (s.length() == 8)
{
  sb.append(s.substring(6));
}
else if (s.length() == 2)
{
  sb.append(s);
}
else
{
  sb.append("0" + s);
}
}

return sb.toString();
}

}
