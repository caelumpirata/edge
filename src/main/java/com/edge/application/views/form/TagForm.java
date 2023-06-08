package com.edge.application.views.form;

import java.time.LocalDate;
import java.text.DecimalFormat;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;

import com.edge.application.views.MainLayout;
import com.edge.application.views.Grid.SourceList;
import com.edge.application.views.Grid.TagList;
import com.edge.application.views.Grid.UserList;
import com.edge.application.views.Grid.elementList;
import com.edge.application.views.Table.ElementTable;
import com.edge.application.views.Table.SourceTable;
import com.edge.application.views.Table.TagTable;
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

@PageTitle("Tag Form")
@Route(value = "TagForm" ,layout = MainLayout.class)
public class TagForm extends VerticalLayout implements HasUrlParameter<String>{
	
	public static final String ROUTE_NAME="TagForm";
	
	TextField txt_tag_id     = new TextField("Tag Id");
	TextField txt_tag_name   = new TextField("Tag Name");
	TextField txt_ref_address    = new TextField("Tag Refrence (Address)");
	TextField txt_reg_address    = new TextField("Tag Register (Address)");
	ComboBox<String> data_type = new ComboBox<>("Data Type");
	TextField txt_unit    = new TextField("Unit");
	
	
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
	    
	    data_type.setAllowCustomValue(true); 
	    data_type.setItems("float","int","double","boolean");
	    
	    txt_ref_address.setAllowedCharPattern("[0-9]");
	    txt_reg_address.setAllowedCharPattern("[0-9]");
	    txt_unit.setAllowedCharPattern("[0-9]");
       
        // btn style
		btn_save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_next.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		
		// source id autoincrement code
		for (TagTable sou_table : service.tag_list()) {

			getid = sou_table.getTag_id().toString();
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
		txt_tag_id.setValue(final_id);
		txt_tag_id.setReadOnly(true);
		
		
		// Validation code
		binder.forField(txt_tag_id).asRequired("Enter Tag ID").bind(Validation::getString,Validation::setString);
		binder.forField(txt_tag_name).asRequired("Enter Tag Name").bind(Validation::getString,Validation::setString);
		binder.forField(txt_ref_address).asRequired("Enter Tag Reference Address").bind(Validation::getString,Validation::setString);
		binder.forField(txt_reg_address).asRequired("Enter Tag Register Address").bind(Validation::getString,Validation::setString);
		binder.forField(data_type).asRequired("Select Data Type").bind(Validation::getString,Validation::setString);
		binder.forField(txt_unit).asRequired("Enter Unit").bind(Validation::getString,Validation::setString);

		
		
		if(!para.equals("0"))
        {
			for(TagTable stt:service.tag_list_id(main_id))
			{
				txt_tag_id.setValue(stt.getTag_id());
				txt_tag_name.setValue(stt.getTag_name());
				txt_ref_address.setValue(stt.getTagRefAddress());
				txt_reg_address.setValue(stt.getTagRegAddress());
				data_type.setValue(stt.getDataType());
				txt_unit.setValue(stt.getUnit());
				
				
			}
        }
		
		// all click Event code
		btn_save.addClickListener(s->{
			if (binder.writeBeanIfValid(entity)) 
			{
				
				
					if(para.equals("0"))
            		{
						String tag_id="",tag_name="",ref_add="",reg_add="",d_type="",t_unit="";
						
						tag_id    = txt_tag_id.getValue().toString();
						tag_name  = txt_tag_name.getValue().toString();
						ref_add    	   = txt_ref_address.getValue().toString();
						reg_add    	   = txt_reg_address.getValue().toString();
						d_type    	   = data_type.getValue().toString();
						t_unit    	   = txt_unit.getValue().toString();
										
            		  
						TagTable st = new TagTable();
						st.setTag_id(tag_id);
						st.setTag_name(tag_name);
						st.setTagRefAddress(ref_add);
						st.setTagRegAddress(reg_add);
						st.setDataType(d_type);
						st.setUnit(t_unit);
						
						st.setRowid(genUniqueID());
						service.tag_save(st);
						
						Notification.show("Tag has been created successfully...!");
						UI.getCurrent().navigate(TagList.class);
            		
            		} // paara check equals 0 then insert or 1 then edit recored
					else
					{
                        String tag_id="",tag_name="",ref_add="",reg_add="",d_type="",t_unit="";
						
						tag_id    = txt_tag_id.getValue().toString();
						tag_name  = txt_tag_name.getValue().toString();
						ref_add    	   = txt_ref_address.getValue().toString();
						reg_add    	   = txt_reg_address.getValue().toString();
						d_type    	   = data_type.getValue().toString();
						t_unit    	   = txt_unit.getValue().toString();
										
            		  
						TagTable st = new TagTable();
						st.setTag_id(tag_id);
						st.setTag_name(tag_name);
						st.setTagRefAddress(ref_add);
						st.setTagRegAddress(reg_add);
						st.setDataType(d_type);
						st.setUnit(t_unit);
						st.setId(main_id);
						st.setRowid(genUniqueID());
						service.tag_save(st);
						
						Notification.show("Tag has been created successfully...!");
						UI.getCurrent().navigate(TagList.class);
        		       }
					
					
        		
				
				
			}
			else
			{
				Notification.show("Action Denied", 3000, Position.MIDDLE);
			}
			
		});
		
		btn_next.addClickListener(s->{
			if (binder.writeBeanIfValid(entity)) 
			{
				String tag_id="";
				
				tag_id    = txt_tag_id.getValue().toString();
				if(service.check_Tag_id(tag_id))
        		{
					//UI.getCurrent().navigate( UserDetailsForm.class);
					UI.getCurrent().navigate(TagForm.ROUTE_NAME + "/" + tag_id);
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
		formLayout.add( txt_tag_id, txt_tag_name, txt_ref_address,txt_reg_address,data_type,txt_unit, btn_layout );
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
