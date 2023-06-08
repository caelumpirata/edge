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
import com.edge.application.views.Grid.elementList;
import com.edge.application.views.Table.ElementTable;
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

@PageTitle("Element Form")
@Route(value = "ElementForm" ,layout = MainLayout.class)
public class ElementForm extends VerticalLayout implements HasUrlParameter<String>{
	
	public static final String ROUTE_NAME="ElementForm";
	
	TextField txt_element_id     = new TextField("Element Id");
	TextField txt_element_name   = new TextField("Element Name");
	ComboBox<String> mosbus_point_type = new ComboBox<>("Modbus Point Type");
	
	
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
	    
	    mosbus_point_type.setAllowCustomValue(true); 
	    mosbus_point_type.setItems("01:COIL STATUS","02:INPUT STATUS","03:HOLDING REGISTER","04:INPUT REGISTER");   
       
        // btn style
		btn_save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn_next.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		
		// source id autoincrement code
		for (ElementTable sou_table : service.element_list()) {

			getid = sou_table.getElement_id().toString();
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
		txt_element_id.setValue(final_id);
		txt_element_id.setReadOnly(true);
		
		
		// Validation code
		binder.forField(txt_element_id).asRequired("Enter Element ID").bind(Validation::getString,Validation::setString);
		binder.forField(txt_element_name).asRequired("Enter Element Name").bind(Validation::getString,Validation::setString);
		binder.forField(mosbus_point_type).asRequired("Select Modbus Point Type").bind(Validation::getString,Validation::setString);
		
		
		if(!para.equals("0"))
        {
			for(ElementTable stt:service.element_list_id(main_id))
			{
				txt_element_id.setValue(stt.getElement_id());
				txt_element_name.setValue(stt.getElement_name());
				mosbus_point_type.setValue(stt.getModbus_pointType());
				
			}
        }
		
		// all click Event code
		btn_save.addClickListener(s->{
			if (binder.writeBeanIfValid(entity)) 
			{
				
				if(!service.check_Source(txt_element_name.getValue()))
        		{
					if(para.equals("0"))
            		{
						String elem_id="",elem_name="",refe="",reg="",type="";
						
						elem_id    = txt_element_id.getValue().toString();
						elem_name  = txt_element_name.getValue().toString();
						type   	   = mosbus_point_type.getValue().toString();
						
            		  
						ElementTable st = new ElementTable();
						st.setElement_id(elem_id);
						st.setElement_name(elem_name);
						st.setModbus_pointType(type);
						st.setRowid(genUniqueID());
						service.element_save(st);
						
						Notification.show("Element has been created successfully...!");
						UI.getCurrent().navigate(elementList.class);
            		
            		} // paara check equals 0 then insert or 1 then edit recored
					else
					{
                        String elem_id="",elem_name="",refe="",reg="",type="";
						
						elem_id    = txt_element_id.getValue().toString();
						elem_name  = txt_element_name.getValue().toString();
						type   	   = mosbus_point_type.getValue().toString();
						
            		  
						ElementTable st = new ElementTable();
						st.setElement_id(elem_id);
						st.setElement_name(elem_name);
						st.setModbus_pointType(type);
						st.setId(main_id);
						st.setRowid(genUniqueID());
                          service.element_save(st);
						
						Notification.show("Element has been created successfully...!");
						UI.getCurrent().navigate(elementList.class);
        		       }
					
					
        		}// source check if condition
				else
				{
					Notification.show("User Name Already In A Record...!");
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
				String element_id="";
				
				element_id    = txt_element_id.getValue().toString();
				if(service.check_Element_id(element_id))
        		{
					//UI.getCurrent().navigate( UserDetailsForm.class);
					UI.getCurrent().navigate(ElementForm.ROUTE_NAME + "/" + element_id);
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
		formLayout.add( txt_element_id, txt_element_name, mosbus_point_type, btn_layout );
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
