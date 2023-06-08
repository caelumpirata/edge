package com.edge.application.views.form;

import java.time.LocalDate;
import java.text.DecimalFormat;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;

import com.edge.application.views.MainLayout;
import com.edge.application.views.Grid.SourceList;
import com.edge.application.views.Grid.UserList;
import com.edge.application.views.Table.SourceTable;
import com.edge.application.views.Table.userTable;
import com.edge.application.views.Table.Validation;
import com.edge.application.views.service.EdgeService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
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

@PageTitle("User Form")
@Route(value = "UserForm" ,layout = MainLayout.class)
public class UserForm extends VerticalLayout implements HasUrlParameter<String>{
	
	public static final String ROUTE_NAME="UserForm";
	
	TextField txt_user_id     = new TextField("User Id");
	TextField txt_user_name   = new TextField("User Name");
	TextField txt_password    = new TextField("Password");
	TextField txt_pt_password      = new TextField("Password Again");
	ComboBox<String> tri = new ComboBox<>("Maximum Try");
	ComboBox<String> status = new ComboBox<>("Status");
	DatePicker date_install  = new DatePicker("Install Date");
	DatePicker date_modified = new DatePicker("Modified Date");
	
	Button btn_save = new Button("SAVE");
	Button btn_next = new Button("NEXT");
	
	HorizontalLayout btn_layout = new HorizontalLayout();
	FormLayout formLayout       = new FormLayout();
	
	Binder<Validation> binder  =new Binder<Validation>();
	Validation entity          =new Validation();
	
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
	    main_id=Long.parseLong(para);
	    
	    tri.setAllowCustomValue(true); 
        tri.setItems("3","4","5","6");   
        status.setAllowCustomValue(true); 
        status.setItems("Lock","Unlock");   
        // btn style
		btn_save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_next.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		
		// source id autoincrement code
		for (userTable sou_table : service.user_id_list()) {

			getid = sou_table.getUser_id().toString();
		}

		if (!getid.equals("")) {
			int_id = Integer.parseInt(getid);
			int_id = int_id + 1;
			if (int_id < 10) {

				final_id = "000" + int_id;
			} else {
				final_id = "00" + int_id;
			}
		} else {
			int_id = 1;
			if (int_id < 10) {

				final_id = "000" + int_id;
			} else {
				final_id = "00" + int_id;
			}
		}
		txt_user_id.setValue(final_id);
		txt_user_id.setReadOnly(true);
		
		
		// Validation code
		binder.forField(txt_user_id).asRequired("Enter User ID").bind(Validation::getString,Validation::setString);
		binder.forField(txt_user_name).asRequired("Enter User Name").bind(Validation::getString,Validation::setString);
		binder.forField(txt_password).asRequired("Enter Password").bind(Validation::getString,Validation::setString);
		binder.forField(txt_pt_password).asRequired("Enter Password Again").bind(Validation::getString,Validation::setString);
		binder.forField(tri).asRequired("Select Maximum Try").bind(Validation::getString,Validation::setString);
		binder.forField(status).asRequired("Status").bind(Validation::getString,Validation::setString);

		binder.forField(date_install).asRequired("Enter Install Date").bind(Validation::getFromDate,Validation::setFromDate);
		binder.forField(date_modified).asRequired("Enter Modified Date").bind(Validation::getFromDate,Validation::setFromDate);
		
		if(!para.equals("0"))
        {
			for(userTable stt:service.user_list_id(main_id))
			{
				txt_user_id.setValue(stt.getUser_id());
				txt_user_name.setValue(stt.getuser_name());
				txt_password.setValue(stt.getpassword());
				txt_pt_password.setValue(stt.getpt_password());
				tri.setValue(stt.getmx_tri());
				status.setValue(stt.getStatus());
				date_install.setValue(LocalDate.parse(stt.getImstall_date()));
				date_modified.setValue(LocalDate.parse(stt.getModifide_date()));
			}
        }
		
		// all click Event code
		btn_save.addClickListener(s->{
			if (binder.writeBeanIfValid(entity)) 
			{
				
				if(!service.check_Source(txt_user_name.getValue()))
        		{
					if(para.equals("0"))
            		{
						String user_id="",user_name="",password="",pt_password="",instal_date="",modi_date="",status_lock="",tri_str="";
						
						user_id    = txt_user_id.getValue().toString();
						user_name  = txt_user_name.getValue().toString();
						password     = txt_password.getValue().toString();
						pt_password   = txt_pt_password.getValue().toString();
						tri_str   		= tri.getValue().toString();
						status_lock   		= status.getValue().toString();
						
						instal_date  = date_install.getValue().toString();
						modi_date    = date_modified.getValue().toString();
						
						if(!password.equals(pt_password)) {
							Notification.show("Password not same please try again!", 3000, Position.MIDDLE);
						}
            		   else{
						userTable st = new userTable();
						st.setUser_id(user_id);
						st.setuser_name(user_name);
						st.setpassword(password);
						st.setpt_password(pt_password);
						st.setmx_tri(tri_str);
						st.setStatus(status_lock);
						st.setNumtries(""+0);
						st.setImstall_date(instal_date);
						st.setModifide_date(modi_date);
						st.setRowid(genUniqueID());
						service.user_save(st);
						
						Notification.show("User has been created successfully...!");
						UI.getCurrent().navigate(UserDetailsForm.class);
            		}
            		} // paara check equals 0 then insert or 1 then edit recored
					else
					{
                        String user_id="",user_name="",password="",pt_password="",instal_date="",modi_date="",status_lock="",tri_str="";
						
						user_id    = txt_user_id.getValue().toString();
						user_name  = txt_user_name.getValue().toString();
						password     = txt_password.getValue().toString();
						pt_password   = txt_pt_password.getValue().toString();
						tri_str   		= tri.getValue().toString();
						status_lock   		= status.getValue().toString();
						
						instal_date  = date_install.getValue().toString();
						modi_date    = date_modified.getValue().toString();
						
						if(!password.equals(pt_password)) {
							Notification.show("Password not same please try again!", 3000, Position.MIDDLE);
						}else{
						
						userTable st = new userTable();
						st.setUser_id(user_id);
						st.setuser_name(user_name);
						st.setpassword(password);
						st.setpt_password(pt_password);
						st.setmx_tri(tri_str);
						st.setStatus(status_lock);
						st.setNumtries(""+0);
						st.setImstall_date(instal_date);
						st.setModifide_date(modi_date);
						st.setId(main_id);
						st.setRowid(genUniqueID());
						service.user_save(st);
						Notification.show("User has been created successfully...!");
						UI.getCurrent().navigate(UserList.class);
        		       }
					}
					
        		}// source check if condition
				else
				{
					Notification.show("User Name Already In A Record...!");
				}
				
				
			}// biner if condition
			else
			{
				Notification.show("Action Denied", 3000, Position.MIDDLE);
			}
			
		});
		
		btn_next.addClickListener(s->{
			if (binder.writeBeanIfValid(entity)) 
			{
				String user_id="";
				
				user_id    = txt_user_id.getValue().toString();
				if(service.check_User_id(user_id))
        		{
					//UI.getCurrent().navigate( UserDetailsForm.class);
					UI.getCurrent().navigate(UserDetailsForm.ROUTE_NAME + "/" + user_id);
        		}else {
        			Notification.show("Action Denied,Please Save Entry First", 3000, Position.MIDDLE);
        		}
			}// biner if condition
			else
			{
				Notification.show("Action Denied,Please Save Entry First", 3000, Position.MIDDLE);
			}
			
		});
				
		
		 // layout add code
		btn_layout.add(btn_save, btn_next);
		formLayout.add( txt_user_id, txt_user_name, txt_password, txt_pt_password,tri,status, date_install, date_modified, btn_layout );
		formLayout.setResponsiveSteps(
		        // Use one column by default
		        new ResponsiveStep("0", 1)
		        );
		add(formLayout);
		
		
	}
	
	@Override
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
